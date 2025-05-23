package com.example.schedulerproject.dto;

import com.example.schedulerproject.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class ScheduleRequestDto {
    private String contentTodo;
    private String username;
    private String password;
//    private LocalDateTime createDate;
//    private LocalDateTime updateDate;

}
