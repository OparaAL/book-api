package com.book.bookapi.repository.user.credentials;

import com.book.bookapi.model.user.credentials.GoogleCredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoogleCredentialsRepository extends JpaRepository<GoogleCredentialsEntity, Long> {
    Optional<GoogleCredentialsEntity> findByEmail(String email);
    Optional<GoogleCredentialsEntity> findByClientId(String id);
}
