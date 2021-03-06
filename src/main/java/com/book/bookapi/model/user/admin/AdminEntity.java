package com.book.bookapi.model.user.admin;

import com.book.bookapi.model.BaseEntity;
import com.book.bookapi.model.user.UserEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "admin_user")
@Data
@NoArgsConstructor
public class AdminEntity extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public AdminEntity(UserEntity user){
        this.user = user;
    }

}
