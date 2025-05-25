package com.example.schedulerproject.exception;

import org.springframework.http.HttpStatus;

public class ScheduleException extends ApiException {

    public ScheduleException(Long scheduleId) {
        super("Schedule not found or deleted : " + scheduleId, HttpStatus.NOT_FOUND);
    }
}
