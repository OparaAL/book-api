package com.book.bookapi.service.interfaces.user;

import com.book.bookapi.dto.request.SignUpDto;
import com.book.bookapi.dto.user.UserDto;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public interface UserService {

    UserDto createUser(SignUpDto signUpDto);
    UserDto createUserWithOAuth2(OAuth2AuthenticationToken oAuth2Token);
}
