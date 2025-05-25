package com.example.schedulerproject.controller;

import com.example.schedulerproject.dto.PagingRequestDto;
import com.example.schedulerproject.dto.PagingResponseDto;
import com.example.schedulerproject.dto.ScheduleRequestDto;
import com.example.schedulerproject.dto.ScheduleResponseDto;
import com.example.schedulerproject.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scheduler")
@RequiredArgsConstructor  // DI
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @RequestBody ScheduleRequestDto dto
    ) {
        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public List<ScheduleResponseDto> findAllSchedules(
            @RequestParam(required = false) String updateDate,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Long userId)
     {
        return scheduleService.findAllSchedules(updateDate,username,userId);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(
            @PathVariable long scheduleId) {
        return new ResponseEntity<>(scheduleService.findScheduleById(scheduleId),HttpStatus.OK);
    }

    @GetMapping("/page")
    public PagingResponseDto findSchedulesPage(
            @RequestParam int pageNum,
            @RequestParam int pageSize) {
        return scheduleService.findSchedulesPage(pageNum,pageSize);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.updateSchedule(scheduleId,dto),HttpStatus.OK);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long scheduleId,
            @RequestBody ScheduleRequestDto dto) {
        scheduleService.deleteSchedule(scheduleId,dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
