package com.book.bookapi.configuration;

import com.book.bookapi.model.AccountType;
import com.book.bookapi.model.user.credentials.BaseCredentialsEntity;
import com.book.bookapi.utils.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class SecurityUser implements UserDetails {

    private final String username;
    private final String password;

    public SecurityUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }


    public static UserDetails fromUser(BaseCredentialsEntity user, AccountType accountType, String clientId){
        return new org.springframework.security.core.userdetails.User(
                String.format("%s|%s|%s", user.getEmail(), accountType, clientId != null ? clientId : ""),
                user.getPassword() != null ? user.getPassword() : StringUtils.generateSecretString(20),
                user.getRole().getAuthorities()
        );
    }
}
