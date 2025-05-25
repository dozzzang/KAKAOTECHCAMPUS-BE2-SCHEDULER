package com.example.schedulerproject.service;

import com.example.schedulerproject.dto.PagingResponseDto;
import com.example.schedulerproject.dto.ScheduleResponseDto;
import com.example.schedulerproject.entity.Schedule;
import com.example.schedulerproject.repository.ScheduleRepository;

import java.util.List;

//공통 관심사에 따라 하나의 Interface보단 여러 Interface ISP
public interface ScheduleReader {
    List<ScheduleResponseDto> findAllSchedules(String updateDate, String username,Long userId);
    ScheduleResponseDto findScheduleById(Long scheduleId);
    PagingResponseDto findSchedulesPage(int pageNum, int pageSize);
}
