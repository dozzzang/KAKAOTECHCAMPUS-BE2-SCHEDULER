package com.example.schedulerproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class User {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public User(String username, String password, String email, LocalDateTime createDate, LocalDateTime updateDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
