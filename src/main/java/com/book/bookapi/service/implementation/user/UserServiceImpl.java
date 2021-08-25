package com.book.bookapi.service.implementation.user;

import com.book.bookapi.dto.request.SignUpDto;
import com.book.bookapi.dto.user.UserDto;
import com.book.bookapi.exceptions.WrongUserException;
import com.book.bookapi.mapper.user.UserMapper;
import com.book.bookapi.model.user.credentials.CredentialsEntity;
import com.book.bookapi.model.user.UserEntity;
import com.book.bookapi.model.user.reader.ReaderEntity;
import com.book.bookapi.repository.user.ReaderRepository;
import com.book.bookapi.repository.user.UserRepository;
import com.book.bookapi.service.interfaces.user.CredentialsService;
import com.book.bookapi.service.interfaces.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final CredentialsService credentialsService;
    private final ReaderRepository readerRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Transactional
    public UserDto createUser(SignUpDto signUpDto) {
        UserDto userDto = new UserDto();
        switch (signUpDto.getRole()){
            case READER:
                userDto = createReader(signUpDto);
                break;

            default: throw new WrongUserException("Wrong user");
        }
        return userDto;
    }

    private UserDto createReader(SignUpDto signUpDto){

        CredentialsEntity credentials = credentialsService.create(signUpDto);
        UserEntity user = userMapper.signUpToUser(signUpDto);

        ReaderEntity reader = new ReaderEntity(credentials, user);

        userRepository.save(user);
        readerRepository.save(reader);

        return userMapper.userToDto(user);
    }
}
