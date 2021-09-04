package com.book.bookapi.service.implementation.user;

import com.book.bookapi.dto.request.SignUpDto;
import com.book.bookapi.mapper.user.ApplicationCredentialsMapper;
import com.book.bookapi.exceptions.ItemAlreadyExistsException;
import com.book.bookapi.model.user.credentials.ApplicationCredentialsEntity;
import com.book.bookapi.model.user.credentials.GoogleCredentialsEntity;
import com.book.bookapi.repository.user.ApplicationCredentialsRepository;
import com.book.bookapi.repository.user.GoogleCredentialsRepository;
import com.book.bookapi.service.interfaces.user.CredentialsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CredentialsServiceImpl implements CredentialsService {

    private final ApplicationCredentialsRepository applicationCredentialsRepository;
    private final GoogleCredentialsRepository googleCredentialsRepository;
    private final ApplicationCredentialsMapper credentialsMapper;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public ApplicationCredentialsEntity createApplicationCredentials(SignUpDto signUpDto) {
        applicationCredentialsRepository.findByEmail(signUpDto.getEmail())
                .ifPresent(el -> {throw new ItemAlreadyExistsException("User with such email already exists.");});
        ApplicationCredentialsEntity credentials = credentialsMapper.signUpDtoToCredentials(signUpDto);

        credentials.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        credentials.setCreationDate(LocalDateTime.now());

        return applicationCredentialsRepository.save(credentials);
    }

    @Transactional
    public GoogleCredentialsEntity createGoogleCredentials(OAuth2AuthenticationToken oAuth2Token) {

        OAuth2User oAuth2User = oAuth2Token.getPrincipal();
        Map<String,Object> attributes =  oAuth2User.getAttributes();

        Optional<GoogleCredentialsEntity> findCredentials = googleCredentialsRepository.findByEmail(attributes.get("email").toString());
        if(findCredentials.isPresent()) return findCredentials.get();
        else {
            GoogleCredentialsEntity credentials = new GoogleCredentialsEntity();
            credentials.setEmail(attributes.get("email").toString());
            credentials.setPassword(null);
            credentials.setCreationDate(LocalDateTime.now());
            return googleCredentialsRepository.save(credentials);
        }
    }

}
