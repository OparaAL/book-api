package com.book.bookapi.dto.request;

import com.book.bookapi.model.user.credentials.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    private String email;
    private String token;
    private List<UserRole> userRoles;
    private String refreshToken;
}