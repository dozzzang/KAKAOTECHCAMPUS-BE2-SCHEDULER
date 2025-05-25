package com.example.schedulerproject.service;

import com.example.schedulerproject.dto.ScheduleRequestDto;
import com.example.schedulerproject.dto.ScheduleResponseDto;
import com.example.schedulerproject.entity.Schedule;
import com.example.schedulerproject.entity.User;
import com.example.schedulerproject.repository.ScheduleRepository;
import com.example.schedulerproject.repository.UserRepository;
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
    private final UserService userService;

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        scheduleChecker.checkCreateRequest(dto);
        User user = userService.findUserByUsernameAndEmail(dto.getUsername(), dto.getEmail());
        if(user == null) {
            user = userService.createUser(dto);
        } else {
            scheduleChecker.checkPassword(dto.getPassword(), user.getPassword());
        }
        Schedule schedule = new Schedule(dto.getContentTodo(),LocalDateTime.now(),LocalDateTime.now(),user.getUserId());
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(String updateDate, String username,Long userId) {
//        if(updateDate == null && username == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"At least, updateDate or username is required.");
//        }     SRP 개선
        scheduleChecker.checkQueryParams(updateDate,username,userId);
        return scheduleRepository.findAllSchedules(updateDate,username,userId);
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
        User user = userService.findUserByUserIdOrElseThrow(schedule.getUserId());
        scheduleChecker.checkPassword(dto.getPassword(),user.getPassword());

        //user update 필요
        userService.updateUser(user.getUserId(),dto.getUsername(),dto.getEmail());
        int updatedRow = scheduleRepository.updateSchedule(scheduleId,dto.getContentTodo());
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
        User user = userService.findUserByUserIdOrElseThrow(schedule.getUserId());
        scheduleChecker.checkPassword(dto.getPassword(),user.getPassword());
        int deleteRow = scheduleRepository.deleteSchedule(scheduleId);
        if(deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Does not exist id = " + scheduleId);
        }
    }

}
