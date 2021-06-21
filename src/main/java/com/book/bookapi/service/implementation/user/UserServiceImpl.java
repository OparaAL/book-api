package com.book.bookapi.service.implementation.user;

import com.book.bookapi.dto.request.SignUpDto;
import com.book.bookapi.dto.user.UserDto;
import com.book.bookapi.mapper.user.ReaderMapper;
import com.book.bookapi.model.user.CredentialsEntity;
import com.book.bookapi.model.user.ReaderEntity;
import com.book.bookapi.repository.user.ReaderRepository;
import com.book.bookapi.service.interfaces.user.CredentialsService;
import com.book.bookapi.service.interfaces.user.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final CredentialsService credentialsService;
    private final ReaderRepository readerRepository;
    private final ReaderMapper readerMapper;

    public UserServiceImpl(CredentialsService credentialsService, ReaderRepository readerRepository, ReaderMapper readerMapper) {
        this.credentialsService = credentialsService;
        this.readerRepository = readerRepository;
        this.readerMapper = readerMapper;
    }

    @Transactional
    public UserDto createUser(SignUpDto signUpDto) {
        UserDto userDto = new UserDto();
        switch (signUpDto.getRole()){
            case USER:
                userDto = createReader(signUpDto);
                break;
        }
        return userDto;
    }

    private UserDto createReader(SignUpDto signUpDto){

        CredentialsEntity credentials = credentialsService.create(signUpDto);

        ReaderEntity reader = readerMapper.signUpDtoToReader(signUpDto);
        reader.setCredentials(credentials);

        readerRepository.save(reader);

        return readerMapper.readerToReaderDto(reader);
    }
}
