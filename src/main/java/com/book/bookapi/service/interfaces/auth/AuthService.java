package com.book.bookapi.service.interfaces.auth;

import com.book.bookapi.dto.request.LoginRequestDto;
import com.book.bookapi.dto.request.TokenDto;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    TokenDto signIn(LoginRequestDto loginRequestDto);
    TokenDto refreshToken(String refresh, HttpServletRequest request);
}
