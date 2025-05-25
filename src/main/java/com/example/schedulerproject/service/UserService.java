package com.example.schedulerproject.service;

import com.example.schedulerproject.dto.ScheduleRequestDto;
import com.example.schedulerproject.entity.Schedule;
import com.example.schedulerproject.entity.User;

public interface UserService {
    User findUserByUsernameAndEmail(String username, String email);
    User createUser(ScheduleRequestDto dto);
    User findUserByUserIdOrElseThrow(Long userId);
    void updateUser(Long userId, String username, String email);
}
