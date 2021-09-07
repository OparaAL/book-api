package com.book.bookapi.service.interfaces.user;

import com.book.bookapi.dto.request.SignUpDto;
import com.book.bookapi.model.user.credentials.ApplicationCredentialsEntity;
import com.book.bookapi.model.user.credentials.FacebookCredentialsEntity;
import com.book.bookapi.model.user.credentials.GithubCredentialsEntity;
import com.book.bookapi.model.user.credentials.GoogleCredentialsEntity;
import com.book.bookapi.repository.user.credentials.FacebookCredentialsRepository;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2Token;

public interface CredentialsService {
    ApplicationCredentialsEntity createApplicationCredentials(SignUpDto signUpDto);
    GoogleCredentialsEntity createGoogleCredentials(OAuth2AuthenticationToken oAuth2Token);
    FacebookCredentialsEntity createFacebookCredentials(OAuth2AuthenticationToken oAuth2Token);
    GithubCredentialsEntity createGithubCredentials(OAuth2AuthenticationToken oAuth2Token);
}
