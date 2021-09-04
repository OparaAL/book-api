package com.book.bookapi.service.implementation.auth;

import com.book.bookapi.configuration.jwt.JwtProvider;
import com.book.bookapi.dto.request.LoginRequestDto;
import com.book.bookapi.dto.request.TokenDto;
import com.book.bookapi.exceptions.ItemNotFoundException;
import com.book.bookapi.exceptions.JwtAuthenticationException;
import com.book.bookapi.model.AccountType;
import com.book.bookapi.model.user.credentials.ApplicationCredentialsEntity;
import com.book.bookapi.model.user.credentials.BaseCredentialsEntity;
import com.book.bookapi.model.user.credentials.GoogleCredentialsEntity;
import com.book.bookapi.repository.user.ApplicationCredentialsRepository;
import com.book.bookapi.repository.user.GoogleCredentialsRepository;
import com.book.bookapi.service.interfaces.auth.AuthService;
import com.book.bookapi.service.interfaces.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final ApplicationCredentialsRepository applicationCredentialsRepository;
    private final JwtProvider jwtProvider;
    private final GoogleCredentialsRepository googleCredentialsRepository;
    private final UserService userService;

    public TokenDto signIn(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
        ApplicationCredentialsEntity credentials = (ApplicationCredentialsEntity) getCredentials(loginRequestDto.getEmail());
        String token = jwtProvider.createToken(String.format("%s|%s", loginRequestDto.getEmail(), AccountType.APPLICATION), credentials.getRole().name());
        String refreshToken = jwtProvider.createRefreshToken(String.format("%s|%s", loginRequestDto.getEmail(), AccountType.APPLICATION), credentials.getRole().name());
        return new TokenDto(loginRequestDto.getEmail(), token, List.of(credentials.getRole()), refreshToken);
    }

    public TokenDto signInGoogle(HttpServletRequest httpServletRequest) {

        OAuth2AuthenticationToken OAuth2token = (OAuth2AuthenticationToken) httpServletRequest.getAttribute("oauthToken");
        if(OAuth2token == null) throw new IllegalArgumentException();
        GoogleCredentialsEntity credentials = getGoogleCredentialsOrCreate(OAuth2token);
        String token = jwtProvider.createToken(String.format("%s|%s", credentials.getEmail(), AccountType.GOOGLE), credentials.getRole().name());
        String refreshToken = jwtProvider.createRefreshToken(String.format("%s|%s", credentials.getEmail(), AccountType.APPLICATION), credentials.getRole().name());

        return new TokenDto(credentials.getEmail(), token, List.of(credentials.getRole()), refreshToken);
    }

    public TokenDto refreshToken(String refresh, HttpServletRequest request) {
        String emailFromJwt = jwtProvider.getEmail(jwtProvider.resolveToken(request));
        Jws<Claims> refreshToken = jwtProvider.getRefreshToken(refresh);

        if(!emailFromJwt.equals(refreshToken.getBody().getSubject())) throw new JwtAuthenticationException("Invalid token");

        BaseCredentialsEntity credentials = getCredentials(refreshToken.getBody().getSubject());

        String updatedToken = jwtProvider.createToken(credentials.getEmail(), credentials.getRole().name());
        String updatedRefresh = jwtProvider.createRefreshToken(credentials.getEmail(), credentials.getRole().name());

        return new TokenDto(credentials.getEmail(), updatedToken, List.of(credentials.getRole()), updatedRefresh);
    }

    private BaseCredentialsEntity getCredentials(String email){
        List<String> emailWithType = Arrays.asList(email.split("\\|"));
        if(emailWithType.size() > 1){
            switch (AccountType.valueOf(emailWithType.get(1))) {
                case APPLICATION:
                    return getApplicationCredentials(emailWithType.get(0));
                case GOOGLE:
                    return getGoogleCredentials(emailWithType.get(0));
                default:
                    return null;
            }
        } else return getApplicationCredentials(email);
    }

    private ApplicationCredentialsEntity getApplicationCredentials(String email){
        return applicationCredentialsRepository.findByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Credentials not found"));
    }

    private GoogleCredentialsEntity getGoogleCredentials(String email){
        return googleCredentialsRepository.findByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Credentials not found"));
    }

    private GoogleCredentialsEntity getGoogleCredentialsOrCreate(OAuth2AuthenticationToken oAuth2AuthenticationToken){

        OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();
        Map<String,Object> attributes =  oAuth2User.getAttributes();

        Optional<GoogleCredentialsEntity> credentials = googleCredentialsRepository.findByEmail(attributes.get("email").toString());
        if(credentials.isEmpty()) userService.createUserWithOAuth2(oAuth2AuthenticationToken);
        return googleCredentialsRepository.findByEmail(attributes.get("email").toString())
                .orElseThrow(() -> new ItemNotFoundException("Credentials not found"));
    }
}
