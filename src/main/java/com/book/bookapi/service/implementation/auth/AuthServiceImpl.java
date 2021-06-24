package com.book.bookapi.service.implementation.auth;

import com.book.bookapi.configuration.jwt.JwtProvider;
import com.book.bookapi.dto.request.LoginRequestDto;
import com.book.bookapi.dto.request.TokenDto;
import com.book.bookapi.exceptions.ItemNotFoundException;
import com.book.bookapi.exceptions.JwtAuthenticationException;
import com.book.bookapi.model.user.CredentialsEntity;
import com.book.bookapi.repository.user.CredentialsRepository;
import com.book.bookapi.service.interfaces.auth.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final CredentialsRepository credentialsRepository;
    private final JwtProvider jwtProvider;


    public AuthServiceImpl(AuthenticationManager authenticationManager, CredentialsRepository credentialsRepository, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.credentialsRepository = credentialsRepository;
        this.jwtProvider = jwtProvider;
    }


    public TokenDto signIn(LoginRequestDto loginRequestDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
        CredentialsEntity credentials = getCredentials(loginRequestDto.getEmail());
        String token = jwtProvider.createToken(loginRequestDto.getEmail(), credentials.getRole().name());
        String refreshToken = jwtProvider.createRefreshToken(loginRequestDto.getEmail(), credentials.getRole().name());
        return new TokenDto(loginRequestDto.getEmail(), token, List.of(credentials.getRole()), refreshToken);
    }


    public TokenDto refreshToken(String refresh, HttpServletRequest request) {
        String emailFromJwt = jwtProvider.getEmail(jwtProvider.resolveToken(request));
        Jws<Claims> refreshToken = jwtProvider.getRefreshToken(refresh);

        if(!emailFromJwt.equals(refreshToken.getBody().getSubject())) throw new JwtAuthenticationException("Invalid token");

        CredentialsEntity credentials = getCredentials(refreshToken.getBody().getSubject());

        String updatedToken = jwtProvider.createToken(credentials.getEmail(), credentials.getRole().name());
        String updatedRefresh = jwtProvider.createRefreshToken(credentials.getEmail(), credentials.getRole().name());

        return new TokenDto(credentials.getEmail(), updatedToken, List.of(credentials.getRole()), updatedRefresh);
    }

    private CredentialsEntity getCredentials(String email){
        return credentialsRepository.findByEmail(email)
                .orElseThrow(() -> new ItemNotFoundException("Credentials not found"));
    }
}
