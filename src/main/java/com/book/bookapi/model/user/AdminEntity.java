package com.book.bookapi.model.user;

import com.book.bookapi.model.BaseUserEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "admin_user")
public class AdminEntity extends BaseUserEntity {

    @OneToOne
    @JoinColumn(name = "credentials_id")
    private CredentialsEntity credentials;

    public AdminEntity() {
    }

    public CredentialsEntity getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialsEntity credentials) {
        this.credentials = credentials;
    }
}
