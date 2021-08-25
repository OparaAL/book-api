package com.book.bookapi.configuration;

import com.book.bookapi.exceptions.ItemNotFoundException;
import com.book.bookapi.model.user.credentials.CredentialsEntity;
import com.book.bookapi.repository.user.CredentialsRepository;
import com.book.bookapi.repository.user.ReaderRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final CredentialsRepository credentialsRepository;
    private final ReaderRepository readerRepository;

    public UserDetailsService(CredentialsRepository credentialsRepository, ReaderRepository readerRepository) {
        this.credentialsRepository = credentialsRepository;
        this.readerRepository = readerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        CredentialsEntity credentials = credentialsRepository
                .findByEmail(s).orElseThrow(() -> new ItemNotFoundException("Credentials not found"));
        return SecurityUser.fromUser(credentials);
    }
}
