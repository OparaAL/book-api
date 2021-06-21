package com.book.bookapi.service.interfaces.user;

import com.book.bookapi.dto.request.SignUpDto;
import com.book.bookapi.model.user.CredentialsEntity;

public interface CredentialsService {
    CredentialsEntity create(SignUpDto signUpDto);
}
