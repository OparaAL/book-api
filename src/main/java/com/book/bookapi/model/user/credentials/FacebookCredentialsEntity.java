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
@Table(name = "facebook_account_credentials")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FacebookCredentialsEntity  extends BaseCredentialsEntity {

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String clientId;

}