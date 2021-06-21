package com.book.bookapi.repository.user;

import com.book.bookapi.model.user.ReaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderRepository extends JpaRepository<ReaderEntity, Long> {
}
