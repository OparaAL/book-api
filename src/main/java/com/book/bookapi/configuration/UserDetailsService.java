package com.book.bookapi.configuration;

import com.book.bookapi.exceptions.ItemNotFoundException;
import com.book.bookapi.model.AccountType;
import com.book.bookapi.model.user.credentials.ApplicationCredentialsEntity;
import com.book.bookapi.model.user.credentials.GoogleCredentialsEntity;
import com.book.bookapi.repository.user.ApplicationCredentialsRepository;
import com.book.bookapi.repository.user.GoogleCredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service("userDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final ApplicationCredentialsRepository applicationCredentialsRepository;
    private final GoogleCredentialsRepository googleCredentialsRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<String> emailWithType = Arrays.asList(s.split("\\|"));
        if(emailWithType.size() > 1){
            switch (AccountType.fromString(emailWithType.get(1))) {
                case APPLICATION:
                    return getApplicationUser(emailWithType.get(0));
                case GOOGLE:
                    return getGoogleUser(emailWithType.get(0));
                default:
                    return null;
            }
        } else{
            return getApplicationUser(s);
        }
    }

    private UserDetails getApplicationUser(String s){
        ApplicationCredentialsEntity credentials = applicationCredentialsRepository
                .findByEmail(s).orElseThrow(() -> new ItemNotFoundException("Credentials not found"));
        return SecurityUser.fromUser(credentials, AccountType.APPLICATION);
    }

    private UserDetails getGoogleUser(String s){
        GoogleCredentialsEntity credentials = googleCredentialsRepository
                .findByEmail(s).orElseThrow(() -> new ItemNotFoundException("Credentials bot found"));
        return SecurityUser.fromUser(credentials, AccountType.GOOGLE);
    }
}
