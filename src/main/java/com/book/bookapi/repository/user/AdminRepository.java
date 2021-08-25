package com.book.bookapi.repository.user;

import com.book.bookapi.model.user.admin.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
}
