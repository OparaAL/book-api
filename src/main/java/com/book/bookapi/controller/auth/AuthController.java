package com.book.bookapi.controller.auth;

import com.book.bookapi.dto.request.LoginRequestDto;
import com.book.bookapi.dto.request.RefreshTokenDto;
import com.book.bookapi.dto.request.SignUpDto;
import com.book.bookapi.dto.request.TokenDto;
import com.book.bookapi.dto.user.UserDto;
import com.book.bookapi.service.interfaces.auth.AuthService;
import com.book.bookapi.service.interfaces.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final OAuth2AuthorizedClientService authorizedClientService;

    @PostMapping("sign-up")
    public ResponseEntity<UserDto> signUp(@Valid @RequestBody SignUpDto signUpDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(signUpDto));
    }

    @GetMapping("test")
    public ResponseEntity<Void> test(OAuth2AuthenticationToken authentication){
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());
        System.out.println(client.getRefreshToken());
        System.out.println(client.getAccessToken());
        System.out.println(client.getClientRegistration());
        System.out.println(client.getPrincipalName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("sign-in")
    public ResponseEntity<TokenDto> signIn(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.status(HttpStatus.OK).body(authService.signIn(loginRequestDto));
    }

    @PostMapping("refresh")
    public ResponseEntity<TokenDto> refresh(@Valid @RequestBody RefreshTokenDto refreshTokenDto, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(authService.refreshToken(refreshTokenDto.getRefreshToken(), request));
    }

}
