package com.book.bookapi;

import com.book.bookapi.model.AccountType;
import com.book.bookapi.model.user.UserEntity;
import com.book.bookapi.model.user.admin.AdminEntity;
import com.book.bookapi.model.user.credentials.ApplicationCredentialsEntity;
import com.book.bookapi.model.user.credentials.UserRole;
import com.book.bookapi.repository.user.AdminRepository;
import com.book.bookapi.repository.user.credentials.ApplicationCredentialsRepository;
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
    private final ApplicationCredentialsRepository applicatioinCredentialsRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(BookapiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if(applicatioinCredentialsRepository.findByEmail("admin@email.com").isEmpty()){

            ApplicationCredentialsEntity credentials =
                    new ApplicationCredentialsEntity("admin@email.com", passwordEncoder.encode("1234567a"),
                            UserRole.ADMIN, LocalDateTime.now(), null);
            applicatioinCredentialsRepository.save(credentials);

            UserEntity user = new UserEntity("admin", "admin", "admin@email.com", credentials, AccountType.APPLICATION);
            AdminEntity admin = new AdminEntity(user);

            credentials.setUser(user);

            userRepository.save(user);
            applicatioinCredentialsRepository.save(credentials);
            adminRepository.save(admin);
        }
    }
}
