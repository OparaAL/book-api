package com.book.bookapi.configuration;

import com.book.bookapi.exceptions.ItemNotFoundException;
import com.book.bookapi.model.AccountType;
import com.book.bookapi.model.user.credentials.ApplicationCredentialsEntity;
import com.book.bookapi.model.user.credentials.GoogleCredentialsEntity;
import com.book.bookapi.repository.user.credentials.ApplicationCredentialsRepository;
import com.book.bookapi.repository.user.credentials.GoogleCredentialsRepository;
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
                    return getApplicationCustomUser(emailWithType.get(0));
                case GOOGLE:
                    return getGoogleCustomUser(emailWithType.get(2));
                default:
                    return null;
            }
        } else{
            return getApplicationCustomUser(s);
        }
    }

    /*private UserDetails getApplicationUser(String email){
        ApplicationCredentialsEntity credentials = applicationCredentialsRepository
                .findByEmail(email).orElseThrow(() -> new ItemNotFoundException("Credentials not found"));
        return SecurityUser.fromUser(credentials, AccountType.APPLICATION, null);
    }

    private UserDetails getGoogleUser(String clientId){
        GoogleCredentialsEntity credentials = googleCredentialsRepository
                .findByClientId(clientId).orElseThrow(() -> new ItemNotFoundException("Credentials bot found"));
        return SecurityUser.fromUser(credentials, AccountType.GOOGLE, clientId);
    }*/

    private UserDetails getApplicationCustomUser(String email){
        ApplicationCredentialsEntity credentials = applicationCredentialsRepository
                .findByEmail(email).orElseThrow(() -> new ItemNotFoundException("Credentials not found"));
        return CustomUser.fromCustomUser(credentials, AccountType.APPLICATION, null);
    }

    private UserDetails getGoogleCustomUser(String clientId){
        GoogleCredentialsEntity credentials = googleCredentialsRepository
                .findByClientId(clientId).orElseThrow(() -> new ItemNotFoundException("Credentials bot found"));
        return CustomUser.fromCustomUser(credentials, AccountType.GOOGLE, clientId);
    }
}
