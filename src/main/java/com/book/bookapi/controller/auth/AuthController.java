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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("sign-up")
    public ResponseEntity<UserDto> signUp(@Valid @RequestBody SignUpDto signUpDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(signUpDto));
    }

    @GetMapping("login/oauth2/google")
    public ResponseEntity<TokenDto> test(HttpServletRequest httpServletRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signInGoogle(httpServletRequest));
    }

    @GetMapping("check")
    public ResponseEntity<Void> check(Authentication authentication){
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
