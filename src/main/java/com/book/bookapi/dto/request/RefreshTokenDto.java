package com.book.bookapi.dto.request;

import javax.validation.constraints.NotBlank;

public class RefreshTokenDto {

    @NotBlank
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
