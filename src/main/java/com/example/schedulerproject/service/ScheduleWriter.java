package com.example.schedulerproject.service;

import com.example.schedulerproject.dto.ScheduleRequestDto;
import com.example.schedulerproject.dto.ScheduleResponseDto;
//ISP
public interface ScheduleWriter {
    ScheduleResponseDto updateSchedule(Long scheduleId, ScheduleRequestDto dto);
    void deleteSchedule(Long scheduleId,ScheduleRequestDto dto);
    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);
}
