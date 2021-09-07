package com.book.bookapi.configuration;

import com.book.bookapi.model.AccountType;
import com.book.bookapi.model.user.credentials.BaseCredentialsEntity;
import com.book.bookapi.utils.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUser extends User {

    private AccountType accountType;
    private String clientId;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities, AccountType accountType, String clientId) {
        super(username, password, authorities);
        this.accountType = accountType;
        this.clientId = clientId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public static UserDetails fromCustomUser(BaseCredentialsEntity user, AccountType accountType, String clientId){
        return new CustomUser(user.getEmail(), user.getPassword() != null ? user.getPassword() : StringUtils.generateSecretString(10),
                user.getRole().getAuthorities(), accountType, clientId);
    }
}
