package com.book.bookapi.model.user.reader;

import com.book.bookapi.model.BaseEntity;
import com.book.bookapi.model.user.credentials.CredentialsEntity;
import com.book.bookapi.model.user.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reader")
@Data
@NoArgsConstructor
public class ReaderEntity extends BaseEntity {

    private String about;

    private LocalDate dateOfBirth;

    @OneToOne
    @JoinColumn(name = "credentials_id")
    private CredentialsEntity credentials;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public ReaderEntity(CredentialsEntity credentials, UserEntity user){
        this.credentials = credentials;
        this.user = user;
    }

}
