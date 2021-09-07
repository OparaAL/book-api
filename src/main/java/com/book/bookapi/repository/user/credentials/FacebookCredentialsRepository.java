package com.book.bookapi.repository.user.credentials;

import com.book.bookapi.model.user.credentials.FacebookCredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacebookCredentialsRepository extends JpaRepository<FacebookCredentialsEntity, Long> {
    Optional<FacebookCredentialsEntity> findByClientId(String id);
}
