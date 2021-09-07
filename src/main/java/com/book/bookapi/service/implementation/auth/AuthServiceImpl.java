package com.book.bookapi.service.implementation.auth;

import com.book.bookapi.configuration.jwt.JwtProvider;
import com.book.bookapi.dto.request.LoginRequestDto;
import com.book.bookapi.dto.request.TokenDto;
import com.book.bookapi.exceptions.ItemNotFoundException;
import com.book.bookapi.exceptions.JwtAuthenticationException;
import com.book.bookapi.exceptions.WrongUserException;
import com.book.bookapi.model.AccountType;
import com.book.bookapi.model.user.credentials.*;
import com.book.bookapi.repository.user.credentials.ApplicationCredentialsRepository;
import com.book.bookapi.repository.user.credentials.FacebookCredentialsRepository;
import com.book.bookapi.repository.user.credentials.GithubCredentialsRepository;
import com.book.bookapi.repository.user.credentials.GoogleCredentialsRepository;
import com.book.bookapi.service.interfaces.auth.AuthService;
import com.book.bookapi.service.interfaces.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final ApplicationCredentialsRepository applicationCredentialsRepository;
    private final JwtProvider jwtProvider;
    private final GoogleCredentialsRepository googleCredentialsRepository;
    private final FacebookCredentialsRepository facebookCredentialsRepository;
    private final GithubCredentialsRepository githubCredentialsRepository;
    private final UserService userService;

    public TokenDto signIn(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
        ApplicationCredentialsEntity credentials = (ApplicationCredentialsEntity) getCredentials(loginRequestDto.getEmail());
        return getAuthToken(credentials, credentials.getUser().getAccountType(), null);
    }

    public TokenDto signInGoogle(HttpServletRequest httpServletRequest) {
        OAuth2AuthenticationToken OAuth2token = getOAuth2Token(httpServletRequest);
        GoogleCredentialsEntity credentials = findGoogleCredentialsOrCreate(OAuth2token);
        return getAuthToken(credentials, credentials.getUser().getAccountType(), OAuth2token);
    }

    public TokenDto signInFacebook(HttpServletRequest httpServletRequest) {
        OAuth2AuthenticationToken OAuth2token = getOAuth2Token(httpServletRequest);
        FacebookCredentialsEntity credentials = findFacebookCredentialsOrCreate(OAuth2token);
        return getAuthToken(credentials, credentials.getUser().getAccountType(), OAuth2token);
    }

    public TokenDto signInGithub(HttpServletRequest httpServletRequest){
        OAuth2AuthenticationToken OAuth2token = getOAuth2Token(httpServletRequest);
        GithubCredentialsEntity credentials = findGithubCredentialsOrCreate(OAuth2token);
        return getAuthToken(credentials, credentials.getUser().getAccountType(), OAuth2token);
    }



    public TokenDto refreshToken(String refresh, HttpServletRequest request) {
        String emailFromJwt = jwtProvider.getEmail(jwtProvider.resolveToken(request));
        Jws<Claims> refreshToken = jwtProvider.getRefreshToken(refresh);

        List<String> emailWithTypeAndClientIdFromJwt = Arrays.asList(emailFromJwt.split("\\|"));
        List<String> emailWithTypeAndClientIdFromRefresh = Arrays.asList(refreshToken.getBody().getSubject().split("\\|"));

        if(AccountType.fromString(emailWithTypeAndClientIdFromJwt.get(1)) != AccountType.APPLICATION &&
                !emailWithTypeAndClientIdFromJwt.get(2).equals(emailWithTypeAndClientIdFromRefresh.get(2))) throw new JwtAuthenticationException("Invalid token");
        else if (!emailWithTypeAndClientIdFromJwt.get(0).equals(emailWithTypeAndClientIdFromRefresh.get(0))) throw new JwtAuthenticationException("Invalid token");

        BaseCredentialsEntity credentials = getCredentials(refreshToken.getBody().getSubject());

        String updatedToken = jwtProvider.createToken(credentials.getEmail(), credentials.getRole().name());
        String updatedRefresh = jwtProvider.createRefreshToken(credentials.getEmail(), credentials.getRole().name());

        return new TokenDto(credentials.getEmail(), updatedToken, List.of(credentials.getRole()), updatedRefresh);
    }

    private BaseCredentialsEntity getCredentials(String email){
        List<String> emailWithTypeAndClientId = Arrays.asList(email.split("\\|"));
        if(emailWithTypeAndClientId.size() > 1){
            switch (AccountType.valueOf(emailWithTypeAndClientId.get(1))) {
                case APPLICATION:
                    return getApplicationCredentials(emailWithTypeAndClientId.get(0));
                case GOOGLE:
                    return getGoogleCredentials(emailWithTypeAndClientId.get(2));
                case FACEBOOK:
                    return getFacebookCredentials(emailWithTypeAndClientId.get(2));
                default:
                    return null;
            }
        } else return getApplicationCredentials(email);
    }



    private ApplicationCredentialsEntity getApplicationCredentials(String email){
        return applicationCredentialsRepository.findByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Credentials not found"));
    }

    private GoogleCredentialsEntity getGoogleCredentials(String clientId){
        return googleCredentialsRepository.findByClientId(clientId)
                .orElseThrow(() -> new ItemNotFoundException("Credentials not found"));
    }

    private FacebookCredentialsEntity getFacebookCredentials(String clientId){
        return facebookCredentialsRepository.findByClientId(clientId)
                .orElseThrow(() -> new ItemNotFoundException(("Credentials not found")));
    }



    private GoogleCredentialsEntity findGoogleCredentialsOrCreate(OAuth2AuthenticationToken oAuth2AuthenticationToken){
        Optional<GoogleCredentialsEntity> credentials = googleCredentialsRepository.findByClientId(oAuth2AuthenticationToken.getName());
        if(credentials.isEmpty()) userService.createUserWithOAuth2(oAuth2AuthenticationToken);
        else return credentials.get();
        return googleCredentialsRepository.findByClientId(oAuth2AuthenticationToken.getName())
                .orElseThrow(() -> new ItemNotFoundException("Credentials not found"));
    }

    private FacebookCredentialsEntity findFacebookCredentialsOrCreate(OAuth2AuthenticationToken oAuth2AuthenticationToken){
        Optional<FacebookCredentialsEntity> credentials = facebookCredentialsRepository.findByClientId(oAuth2AuthenticationToken.getName());
        if(credentials.isEmpty()) userService.createUserWithOAuth2(oAuth2AuthenticationToken);
        else return credentials.get();
        return facebookCredentialsRepository.findByClientId(oAuth2AuthenticationToken.getName())
                .orElseThrow(() -> new ItemNotFoundException("Credentials not found"));
    }

    private GithubCredentialsEntity findGithubCredentialsOrCreate(OAuth2AuthenticationToken oAuth2AuthenticationToken){
        Optional<GithubCredentialsEntity> credentials = githubCredentialsRepository.findByClientId(oAuth2AuthenticationToken.getName());
        if(credentials.isEmpty()) userService.createUserWithOAuth2(oAuth2AuthenticationToken);
        else return credentials.get();
        return githubCredentialsRepository.findByClientId(oAuth2AuthenticationToken.getName())
                .orElseThrow(() -> new ItemNotFoundException("Credentials not found"));
    }



    private OAuth2AuthenticationToken getOAuth2Token(HttpServletRequest httpServletRequest){
        OAuth2AuthenticationToken OAuth2token = (OAuth2AuthenticationToken) httpServletRequest.getAttribute("oauthToken");
        if(OAuth2token == null) throw new IllegalArgumentException();
        return OAuth2token;
    }

    private TokenDto getAuthToken(BaseCredentialsEntity credentials, AccountType accountType, OAuth2AuthenticationToken OAuth2token){
        String token = jwtProvider
                .createToken(String.format("%s|%s|%s", credentials.getEmail(), accountType, OAuth2token != null ? OAuth2token.getName() : null),
                        credentials.getRole().name());
        String refreshToken = jwtProvider
                .createRefreshToken(String.format("%s|%s|%s", credentials.getEmail(), accountType, OAuth2token != null ? OAuth2token.getName() : null),
                        credentials.getRole().name());
        return new TokenDto(credentials.getEmail(), token, List.of(credentials.getRole()), refreshToken);
    }
}
