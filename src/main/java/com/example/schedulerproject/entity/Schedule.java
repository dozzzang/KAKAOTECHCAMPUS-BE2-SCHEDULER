package com.example.schedulerproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@AllArgsConstructor
public class Schedule {
    private Long id;
    private String contentTodo;
    private String username;
    private String password;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public Schedule(String contentTodo,String username,String password,LocalDateTime createDate, LocalDateTime updateDate) {
        this.contentTodo = contentTodo;
        this.username = username;
        this.password = password;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
