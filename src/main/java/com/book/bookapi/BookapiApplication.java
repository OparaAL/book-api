package com.book.bookapi;

import com.book.bookapi.model.user.AdminEntity;
import com.book.bookapi.model.user.CredentialsEntity;
import com.book.bookapi.model.user.UserRole;
import com.book.bookapi.repository.user.AdminRepository;
import com.book.bookapi.repository.user.CredentialsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@SpringBootApplication
public class BookapiApplication implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final CredentialsRepository credentialsRepository;
    private final PasswordEncoder passwordEncoder;

    public BookapiApplication(AdminRepository adminRepository, CredentialsRepository credentialsRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.credentialsRepository = credentialsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(BookapiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if(credentialsRepository.findByEmail("admin@email.com").isEmpty()){
            CredentialsEntity credentials =
                    new CredentialsEntity("admin@email.com", passwordEncoder.encode("1234567a"), UserRole.ADMIN, LocalDateTime.now());

            AdminEntity admin = new AdminEntity();
            admin.setCredentials(credentials);

            credentialsRepository.save(credentials);
            adminRepository.save(admin);
        }
    }
}
