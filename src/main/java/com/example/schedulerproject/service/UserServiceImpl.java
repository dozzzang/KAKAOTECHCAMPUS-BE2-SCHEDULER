package com.example.schedulerproject.service;

import com.example.schedulerproject.dto.ScheduleRequestDto;
import com.example.schedulerproject.entity.User;
import com.example.schedulerproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User findUserByUsernameAndEmail(String username, String email) {
        return userRepository.findUserByUsernameAndEmail(username,email);
    }

    @Override
    public User createUser(ScheduleRequestDto dto) {
        User user = new User(dto.getUsername(),dto.getPassword(),dto.getEmail(), LocalDateTime.now(),LocalDateTime.now());
        return userRepository.saveUser(user);
    }

    @Override
    public User findUserByUserIdOrElseThrow(Long userId) {
        return userRepository.findUserByUserIdOrElseThrow(userId);
    }

    @Override
    public void updateUser(Long userId, String username, String email) {
        userRepository.updateUser(userId, username, email);
    }
}
