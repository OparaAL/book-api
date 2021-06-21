package com.book.bookapi.service.implementation.user;

import com.book.bookapi.dto.request.SignUpDto;
import com.book.bookapi.mapper.user.CredentialsMapper;
import com.book.bookapi.exceptions.ItemAlreadyExistsException;
import com.book.bookapi.model.user.CredentialsEntity;
import com.book.bookapi.repository.user.CredentialsRepository;
import com.book.bookapi.service.interfaces.user.CredentialsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class CredentialsServiceImpl implements CredentialsService {

    private final CredentialsRepository credentialsRepository;
    private final CredentialsMapper credentialsMapper;

    public CredentialsServiceImpl(CredentialsRepository credentialsRepository, CredentialsMapper credentialsMapper) {
        this.credentialsRepository = credentialsRepository;
        this.credentialsMapper = credentialsMapper;
    }

    @Transactional
    public CredentialsEntity create(SignUpDto signUpDto) {
        credentialsRepository.findByEmail(signUpDto.getEmail())
                .ifPresent(el -> {throw new ItemAlreadyExistsException("User with such email already exists.");});
        CredentialsEntity credentials = credentialsMapper.signUpDtoToCredentials(signUpDto);

        credentials.setCreationDate(LocalDateTime.now());
        //TODO add password encoding

        return credentialsRepository.save(credentials);
    }
}
