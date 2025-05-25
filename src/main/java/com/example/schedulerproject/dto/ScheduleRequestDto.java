package com.example.schedulerproject.dto;

import com.example.schedulerproject.entity.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
public class ScheduleRequestDto {
    private String contentTodo;
    private String username;
    private String password;
    private String email;
//    private LocalDateTime createDate;
//    private LocalDateTime updateDate;

}
