package com.example.schedulerproject.repository;

import com.example.schedulerproject.dto.PagingRequestDto;
import com.example.schedulerproject.dto.PagingResponseDto;
import com.example.schedulerproject.dto.ScheduleResponseDto;
import com.example.schedulerproject.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);
    List<ScheduleResponseDto> findAllSchedules(String updateDate,String username,Long userId);
    Schedule findScheduleByScheduleIdOrElseThrow(Long scheduleId);
    int updateSchedule(Long scheduleId,String contentTodo);
    int deleteSchedule(Long scheduleId);
    PagingResponseDto findSchedulesPage(PagingRequestDto pagingRequestDto);
}
