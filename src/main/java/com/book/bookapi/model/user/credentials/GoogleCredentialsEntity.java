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


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "google_account_credentials")
@Getter
@Setter
public class GoogleCredentialsEntity extends BaseCredentialsEntity {

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
