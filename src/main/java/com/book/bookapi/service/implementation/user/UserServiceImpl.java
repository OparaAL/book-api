package com.book.bookapi.service.implementation.user;

import com.book.bookapi.dto.request.SignUpDto;
import com.book.bookapi.dto.user.UserDto;
import com.book.bookapi.exceptions.WrongUserException;
import com.book.bookapi.mapper.user.UserMapper;
import com.book.bookapi.model.AccountType;
import com.book.bookapi.model.user.UserEntity;
import com.book.bookapi.model.user.credentials.*;
import com.book.bookapi.model.user.reader.ReaderEntity;
import com.book.bookapi.repository.user.credentials.GoogleCredentialsRepository;
import com.book.bookapi.repository.user.ReaderRepository;
import com.book.bookapi.repository.user.UserRepository;
import com.book.bookapi.service.interfaces.user.CredentialsService;
import com.book.bookapi.service.interfaces.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final CredentialsService credentialsService;
    private final ReaderRepository readerRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final GoogleCredentialsRepository googleCredentialsRepository;

    @Transactional
    public UserDto createUser(SignUpDto signUpDto) {
        return createReaderWithApplicationCredentials(signUpDto);
    }

    @Transactional
    public UserDto createUserWithOAuth2(OAuth2AuthenticationToken oAuth2Token){

        if(oAuth2Token == null) throw new IllegalArgumentException();

        UserDto userDto;
        OAuth2User oAuth2User = oAuth2Token.getPrincipal();
        Map<String,Object> attributes =  oAuth2User.getAttributes();

        switch (AccountType.fromString(oAuth2Token.getAuthorizedClientRegistrationId())){
            case GOOGLE:
                userDto = createReaderWithGoogleCredentials(oAuth2Token, attributes);
                break;
            case FACEBOOK:
                userDto = createReaderWithFacebookCredentials(oAuth2Token, attributes);
                break;
            case GITHUB:
                userDto = createReaderWithGithubCredentials(oAuth2Token, attributes);
                break;
            default: throw new WrongUserException("Cannot create OAuth2 user");
        }
        return userDto;
    }


    private UserDto createReaderWithApplicationCredentials(SignUpDto signUpDto){

        ApplicationCredentialsEntity credentials = credentialsService.createApplicationCredentials(signUpDto);
        UserEntity user = userMapper.signUpToUser(signUpDto);

        ReaderEntity reader = new ReaderEntity(user);

        user.setAccountType(AccountType.APPLICATION);
        user.setApplicationCredentials(credentials);
        credentials.setRole(UserRole.READER);
        credentials.setUser(user);

        userRepository.save(user);
        readerRepository.save(reader);

        return userMapper.userToDto(user);
    }

    private UserDto createReaderWithGoogleCredentials(OAuth2AuthenticationToken oAuth2Token, Map<String, Object> attributes){

        GoogleCredentialsEntity credentials = credentialsService.createGoogleCredentials(oAuth2Token);
        UserEntity user = new UserEntity();

        ReaderEntity reader = new ReaderEntity(user);

        user.setEmail(credentials.getEmail());
        user.setFirstName(attributes.get("given_name").toString());
        user.setLastName(attributes.get("family_name").toString());
        user.setAccountType(AccountType.GOOGLE);
        user.setGoogleCredentials(credentials);

        credentials.setClientId(oAuth2Token.getName());
        credentials.setUser(user);
        credentials.setRole(UserRole.READER);

        userRepository.save(user);
        readerRepository.save(reader);

        return userMapper.userToDto(user);
    }

    private UserDto createReaderWithFacebookCredentials(OAuth2AuthenticationToken oAuth2Token, Map<String, Object> attributes){

        FacebookCredentialsEntity credentials = credentialsService.createFacebookCredentials(oAuth2Token);
        UserEntity user = new UserEntity();

        ReaderEntity reader = new ReaderEntity(user);

        List<String> firstAndLastName = Arrays.asList(attributes.get("name").toString().split(" "));

        user.setEmail(credentials.getEmail());
        user.setFirstName(firstAndLastName.get(0));
        user.setLastName(firstAndLastName.get(1));
        user.setAccountType(AccountType.FACEBOOK);
        user.setFacebookCredentials(credentials);

        credentials.setClientId(oAuth2Token.getName());
        credentials.setUser(user);
        credentials.setRole(UserRole.READER);

        userRepository.save(user);
        readerRepository.save(reader);

        return userMapper.userToDto(user);
    }

    private UserDto createReaderWithGithubCredentials(OAuth2AuthenticationToken oAuth2Token, Map<String, Object> attributes) {

        GithubCredentialsEntity credentials = credentialsService.createGithubCredentials(oAuth2Token);
        UserEntity user = new UserEntity();

        ReaderEntity reader = new ReaderEntity(user);

        List<String> firstAndLastName = new ArrayList<>();
        if(attributes.get("name") != null) firstAndLastName = Arrays.asList(attributes.get("name").toString().split(" "));

        user.setEmail(credentials.getEmail());
        user.setFirstName(!firstAndLastName.isEmpty() ? firstAndLastName.get(0) : null);
        user.setLastName(!firstAndLastName.isEmpty() ? firstAndLastName.get(1) : null);
        user.setAccountType(AccountType.GITHUB);
        user.setGithubCredentials(credentials);

        credentials.setClientId(oAuth2Token.getName());
        credentials.setUser(user);
        credentials.setRole(UserRole.READER);

        userRepository.save(user);
        readerRepository.save(reader);

        return userMapper.userToDto(user);
    }

}
