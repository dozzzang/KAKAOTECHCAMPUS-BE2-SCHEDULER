package com.example.schedulerproject.service;

import com.example.schedulerproject.dto.ScheduleRequestDto;
import com.example.schedulerproject.exception.PasswordException;
import com.example.schedulerproject.exception.RequiredException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

//SRP 클래스 분리
@Component
public class ScheduleChecker {
        public void checkCreateRequest(ScheduleRequestDto dto) {
            if(dto.getContentTodo() == null || dto.getUsername() == null || dto.getPassword() == null) {
                throw new RequiredException("ContentTodo, username, password are required");
            }
        }
        public void checkUpdateRequest(ScheduleRequestDto dto) {
            if(dto.getContentTodo() == null || dto.getUsername() == null || dto.getPassword() == null) {
                throw new RequiredException("At least, One of  ContentTodo, username, password is required");
            }
        }
        public void checkQueryParams(String updateDate, String username,Long userId) {
            if(updateDate == null && username == null && userId == null) {
                throw new RequiredException("update, username, password are required");
            }
        }

        public void checkPassword(String password, String realPassword) {
            if(!realPassword.equals(password)) {
                throw new PasswordException();
            }
        }
}
