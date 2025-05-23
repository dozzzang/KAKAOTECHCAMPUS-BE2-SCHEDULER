package com.example.schedulerproject.service;

import com.example.schedulerproject.dto.ScheduleRequestDto;
import com.example.schedulerproject.dto.ScheduleResponseDto;
import com.example.schedulerproject.entity.Schedule;
import com.example.schedulerproject.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        Schedule schedule = new Schedule(dto.getContentTodo(),dto.getUsername(),dto.getPassword(),LocalDateTime.now(),LocalDateTime.now());
        return scheduleRepository.saveSchedule(schedule);

    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(String updateDate, String username) {
        if(updateDate == null && username == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"At least, updateDate or username is required.");
        }
        return scheduleRepository.findAllSchedules(updateDate,username);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long scheduleId) {
        Schedule schedule = scheduleRepository.findScheduleByScheduleIdOrElseThrow(scheduleId);
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long scheduleId, ScheduleRequestDto dto) {
        if (dto.getContentTodo() == null || dto.getUsername() == null || dto.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ContentTodo, username, and password are required values.");
        }
        Schedule schedule = scheduleRepository.findScheduleByScheduleIdOrElseThrow(scheduleId);
        if(!schedule.getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Do not match password");
        }
        int updatedRow = scheduleRepository.updateSchedule(scheduleId,dto.getContentTodo(),dto.getUsername());
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + scheduleId);
        }

        Schedule updatedSchedule = scheduleRepository.findScheduleByScheduleIdOrElseThrow(scheduleId);
        return new ScheduleResponseDto(updatedSchedule);
    }

    @Override
    public void deleteSchedule(Long scheduleId, ScheduleRequestDto dto) {
        Schedule schedule = scheduleRepository.findScheduleByScheduleIdOrElseThrow(scheduleId);
        if(!schedule.getPassword().equals(dto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Do not match password");
        }

        int deleteRow = scheduleRepository.deleteSchedule(scheduleId);
        if(deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Does not exist id = " + scheduleId);
        }
    }
}
