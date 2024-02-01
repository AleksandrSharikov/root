package com.hstat.user.model;

import com.hstat.common.dtoModels.UserCard;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Date;

@Slf4j
@Entity
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long userId;
    @Column(name = "telegram_id")
    private long tgId;
    @Column(name = "u_name")
    private String name;
    @Column
    private String password;
    @Column(name = "birth_day")
    private LocalDate birthDay;

    public User(UserCard  card){
        this.userId = card.userId();
        this.tgId = card.tdId();
        this.name = card.name();
        this.birthDay = card.birthDay();
        this.password = card.password();
    }

    public User(){
        this.userId = 0;
        this.tgId = 0;
        this.name = "";
        this.birthDay = LocalDate.now();
        this.password = "";
    }



    public long getUserId() {
        return userId;
    }

    public long getTgId() {
        return tgId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", tgId=" + tgId +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", birthDay=" + birthDay +
                '}';
    }
}
