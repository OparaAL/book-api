package com.book.bookapi.repository.user.credentials;

import com.book.bookapi.model.user.credentials.GithubCredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GithubCredentialsRepository extends JpaRepository<GithubCredentialsEntity, Long> {
    Optional<GithubCredentialsEntity> findByClientId(String id);
}
