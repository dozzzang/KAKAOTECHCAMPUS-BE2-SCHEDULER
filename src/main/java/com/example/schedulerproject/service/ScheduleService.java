package com.example.schedulerproject.service;

import com.example.schedulerproject.dto.ScheduleRequestDto;
import com.example.schedulerproject.dto.ScheduleResponseDto;
import com.example.schedulerproject.entity.Schedule;

import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);
    List<ScheduleResponseDto> findAllSchedules(String updateDate, String username);
    ScheduleResponseDto findScheduleById(Long scheduleId);
    ScheduleResponseDto updateSchedule(Long scheduleId, ScheduleRequestDto dto);
    void deleteSchedule(Long scheduleId,ScheduleRequestDto dto);
}
