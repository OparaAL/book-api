package com.book.bookapi.service.implementation.user;

import com.book.bookapi.dto.request.SignUpDto;
import com.book.bookapi.mapper.user.CredentialsMapper;
import com.book.bookapi.exceptions.ItemAlreadyExistsException;
import com.book.bookapi.model.user.credentials.CredentialsEntity;
import com.book.bookapi.repository.user.CredentialsRepository;
import com.book.bookapi.service.interfaces.user.CredentialsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class CredentialsServiceImpl implements CredentialsService {

    private final CredentialsRepository credentialsRepository;
    private final CredentialsMapper credentialsMapper;
    private final PasswordEncoder passwordEncoder;

    public CredentialsServiceImpl(CredentialsRepository credentialsRepository, CredentialsMapper credentialsMapper,
                                  PasswordEncoder passwordEncoder) {
        this.credentialsRepository = credentialsRepository;
        this.credentialsMapper = credentialsMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public CredentialsEntity create(SignUpDto signUpDto) {
        credentialsRepository.findByEmail(signUpDto.getEmail())
                .ifPresent(el -> {throw new ItemAlreadyExistsException("User with such email already exists.");});
        CredentialsEntity credentials = credentialsMapper.signUpDtoToCredentials(signUpDto);

        credentials.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        credentials.setCreationDate(LocalDateTime.now());

        return credentialsRepository.save(credentials);
    }
}
