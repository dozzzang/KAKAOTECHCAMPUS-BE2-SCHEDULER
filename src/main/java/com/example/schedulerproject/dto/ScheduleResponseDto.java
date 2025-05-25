package com.example.schedulerproject.dto;

import com.example.schedulerproject.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long scheduleId;
    private String contentTodo;
    private String username;
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public ScheduleResponseDto(Schedule schedule) {
        this.scheduleId = schedule.getScheduleId();
        this.contentTodo = schedule.getContentTodo();
        this.username = schedule.getUsername();
        this.email = schedule.getEmail();
        this.createDate = schedule.getCreateDate();
        this.updateDate = schedule.getUpdateDate();
    }
}
