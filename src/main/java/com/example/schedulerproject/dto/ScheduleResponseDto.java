package com.example.schedulerproject.dto;

import com.example.schedulerproject.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String contentTodo;
    private String username;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.contentTodo = schedule.getContentTodo();
        this.username = schedule.getUsername();
        this.createDate = schedule.getCreateDate();
        this.updateDate = schedule.getUpdateDate();
    }
}
