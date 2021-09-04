package com.book.bookapi.model.user;


import com.book.bookapi.model.AccountType;
import com.book.bookapi.model.BaseEntity;
import com.book.bookapi.model.user.credentials.ApplicationCredentialsEntity;
import com.book.bookapi.model.user.credentials.GoogleCredentialsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "base_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends BaseEntity {

    private String firstName;

    private String lastName;

    private String email;

    @OneToOne(mappedBy = "user")
    private ApplicationCredentialsEntity applicationCredentials;

    @OneToOne(mappedBy = "user")
    private GoogleCredentialsEntity googleCredentials;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    public UserEntity(String firstName, String lastName, String email, ApplicationCredentialsEntity applicationCredentials, AccountType accountType){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.applicationCredentials = applicationCredentials;
        this.accountType = accountType;
    }

    public UserEntity(String firstName, String lastName, String email, GoogleCredentialsEntity googleCredentials, AccountType accountType){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.googleCredentials = googleCredentials;
        this.accountType = accountType;
    }
}
