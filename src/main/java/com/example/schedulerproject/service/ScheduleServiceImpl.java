package com.example.schedulerproject.service;

import com.example.schedulerproject.dto.ScheduleRequestDto;
import com.example.schedulerproject.dto.ScheduleResponseDto;
import com.example.schedulerproject.entity.Schedule;
import com.example.schedulerproject.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor // DI
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleChecker scheduleChecker;

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        scheduleChecker.checkCreateRequest(dto);
        Schedule schedule = new Schedule(dto.getContentTodo(),dto.getUsername(),dto.getPassword(),LocalDateTime.now(),LocalDateTime.now());
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(String updateDate, String username) {
//        if(updateDate == null && username == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"At least, updateDate or username is required.");
//        }     SRP 개선
        scheduleChecker.checkQueryParams(updateDate,username);
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
//        if (dto.getContentTodo() == null || dto.getUsername() == null || dto.getPassword() == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ContentTodo, username, and password are required values.");
//        } SRP 개선
        scheduleChecker.checkUpdateRequest(dto);
        Schedule schedule = scheduleRepository.findScheduleByScheduleIdOrElseThrow(scheduleId);
//        if(!schedule.getPassword().equals(dto.getPassword())) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Do not match password");
//        }  SRP 개선
        scheduleChecker.checkPassword(dto.getPassword(),schedule.getPassword());
        int updatedRow = scheduleRepository.updateSchedule(scheduleId,dto.getContentTodo(),dto.getUsername());
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + scheduleId);
        }

        Schedule updatedSchedule = scheduleRepository.findScheduleByScheduleIdOrElseThrow(scheduleId);
        return new ScheduleResponseDto(updatedSchedule);
    }
    @Transactional
    @Override
    public void deleteSchedule(Long scheduleId, ScheduleRequestDto dto) {
        Schedule schedule = scheduleRepository.findScheduleByScheduleIdOrElseThrow(scheduleId);
//        if(!schedule.getPassword().equals(dto.getPassword())) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Do not match password");
//        }     SRP 개선
        scheduleChecker.checkPassword(dto.getPassword(),schedule.getPassword());
        int deleteRow = scheduleRepository.deleteSchedule(scheduleId);
        if(deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Does not exist id = " + scheduleId);
        }
    }

}
