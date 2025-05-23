package com.example.schedulerproject.repository;

import com.example.schedulerproject.dto.ScheduleResponseDto;
import com.example.schedulerproject.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);
    List<ScheduleResponseDto> findAllSchedules(String updateDate,String username);
    Schedule findScheduleByScheduleIdOrElseThrow(Long scheduleId);
    int updateSchedule(Long scheduleId,String contentTodo,String username);
    int deleteSchedule(Long scheduleId);
}
