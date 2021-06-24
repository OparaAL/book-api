package com.book.bookapi.repository.user;

import com.book.bookapi.model.user.CredentialsEntity;
import com.book.bookapi.model.user.ReaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReaderRepository extends JpaRepository<ReaderEntity, Long> {
    Optional<ReaderEntity> findByCredentials(CredentialsEntity credentials);
}
