package com.book.bookapi.model.user;

import com.book.bookapi.model.BaseUserEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "reader")
public class ReaderEntity extends BaseUserEntity {

    private String about;

    private LocalDate dateOfBirth;

    @OneToOne
    @JoinColumn(name = "credentials_id")
    private CredentialsEntity credentials;


    public ReaderEntity() {
    }


    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public CredentialsEntity getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialsEntity credentials) {
        this.credentials = credentials;
    }
}
