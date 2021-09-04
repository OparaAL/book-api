package com.book.bookapi.model.user.reader;

import com.book.bookapi.model.BaseEntity;
import com.book.bookapi.model.user.credentials.ApplicationCredentialsEntity;
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
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public ReaderEntity(UserEntity user){
        this.user = user;
    }

}
