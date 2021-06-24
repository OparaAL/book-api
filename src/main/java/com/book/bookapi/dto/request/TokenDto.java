package com.book.bookapi.dto.request;

import com.book.bookapi.model.user.UserRole;

import java.util.List;

public class TokenDto {
    private String email;
    private String token;
    private List<UserRole> userRoles;
    private String refreshToken;

    public TokenDto() {
    }

    public TokenDto(String email, String token, List<UserRole> userRoles, String refreshToken) {
        this.email = email;
        this.token = token;
        this.userRoles = userRoles;
        this.refreshToken = refreshToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}