package com.example.schedulerproject.service;

import com.example.schedulerproject.dto.ScheduleRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

//SRP 클래스 분리
@Component
public class ScheduleChecker {
        public void checkCreateRequest(ScheduleRequestDto dto) {
            if(dto.getContentTodo() == null || dto.getUsername() == null || dto.getPassword() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Please Check Required field");
            }
        }
        public void checkUpdateRequest(ScheduleRequestDto dto) {
            if(dto.getContentTodo() == null || dto.getUsername() == null || dto.getPassword() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"ContentTodo, username, and password are required values.");
            }
        }
        public void checkQueryParams(String updateDate, String username,Long userId) {
            if(updateDate == null && username == null && userId == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"At least updateDate or username is required");
            }
        }

        public void checkPassword(String password, String realPassword) {
            if(!realPassword.equals(password)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password does not match");
            }
        }
}
