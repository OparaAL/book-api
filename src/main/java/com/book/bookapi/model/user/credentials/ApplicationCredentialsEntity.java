package com.book.bookapi.model.user.credentials;

import com.book.bookapi.model.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "application_account_credentials")
@Getter
@Setter
@NoArgsConstructor
public class ApplicationCredentialsEntity extends BaseCredentialsEntity {

    public ApplicationCredentialsEntity(String email, String password, UserRole role, LocalDateTime creationDate, UserEntity user){
        super(email, password, role, creationDate, user);
    }
}
