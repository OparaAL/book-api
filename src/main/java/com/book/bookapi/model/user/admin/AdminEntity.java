package com.book.bookapi.model.user.admin;

import com.book.bookapi.model.BaseEntity;
import com.book.bookapi.model.user.credentials.CredentialsEntity;
import com.book.bookapi.model.user.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "admin_user")
@Data
@NoArgsConstructor
public class AdminEntity extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "credentials_id")
    private CredentialsEntity credentials;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public AdminEntity(CredentialsEntity credentials, UserEntity user){
        this.credentials = credentials;
        this.user = user;
    }

}
