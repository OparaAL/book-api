package com.book.bookapi.repository.user;

import com.book.bookapi.model.user.credentials.ApplicationCredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationCredentialsRepository extends JpaRepository<ApplicationCredentialsEntity, Long> {
    Optional<ApplicationCredentialsEntity> findByEmail(String email);
}
