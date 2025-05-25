package com.example.schedulerproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@AllArgsConstructor
public class Schedule {
    private Long scheduleId;
    private String contentTodo;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private Long userId;

    private String username;
    private String email;

    public Schedule(String contentTodo, LocalDateTime createDate, LocalDateTime updateDate, Long userId) {
        this.contentTodo = contentTodo;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.userId = userId;
    }
}
