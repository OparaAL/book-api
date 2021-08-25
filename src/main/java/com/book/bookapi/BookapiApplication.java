package com.book.bookapi;

import com.book.bookapi.model.user.UserEntity;
import com.book.bookapi.model.user.admin.AdminEntity;
import com.book.bookapi.model.user.credentials.CredentialsEntity;
import com.book.bookapi.model.user.credentials.UserRole;
import com.book.bookapi.repository.user.AdminRepository;
import com.book.bookapi.repository.user.CredentialsRepository;
import com.book.bookapi.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootApplication
@RequiredArgsConstructor
public class BookapiApplication implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final CredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(BookapiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if(credentialsRepository.findByEmail("admin@email.com").isEmpty()){
            CredentialsEntity credentials =
                    new CredentialsEntity("admin@email.com", passwordEncoder.encode("1234567a"), UserRole.ADMIN, LocalDateTime.now());

            UserEntity user = new UserEntity("admin", "admin", "admin@email.com");
            AdminEntity admin = new AdminEntity(credentials, user);

            userRepository.save(user);
            credentialsRepository.save(credentials);
            adminRepository.save(admin);
        }
    }
}
