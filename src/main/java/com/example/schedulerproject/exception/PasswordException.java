package com.example.schedulerproject.exception;

import org.springframework.http.HttpStatus;

//Lv 5 ScheduleChecker에 의해 추상화. 수정,삭제에서 호출!
public class PasswordException extends ApiException{
    public PasswordException() {
        super("Incorrect password", HttpStatus.UNAUTHORIZED);
    }
}
