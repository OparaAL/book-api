package com.book.bookapi.model.user.credentials;

import com.book.bookapi.model.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseCredentialsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String email;

    protected String password;

    @Enumerated(EnumType.STRING)
    protected UserRole role;

    protected LocalDateTime creationDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public BaseCredentialsEntity(String email, String password, UserRole role, LocalDateTime creationDate, UserEntity user) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.creationDate = creationDate;
        this.user = user;
    }
}
