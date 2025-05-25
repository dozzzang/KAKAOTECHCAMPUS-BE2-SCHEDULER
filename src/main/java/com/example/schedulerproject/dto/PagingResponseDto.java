package com.example.schedulerproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PagingResponseDto {
    private List<ScheduleResponseDto> schedules;
    private int totalPages;
    private int totalSchedules;
    private int currentPage;
    private int pageSize;
}
