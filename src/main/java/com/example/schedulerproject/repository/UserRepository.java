package com.example.schedulerproject.repository;

import com.example.schedulerproject.entity.User;

public interface UserRepository {
    User saveUser(User user);
    User findUserByUsernameAndEmail(String username, String email);
    User findUserByUserIdOrElseThrow(Long userId);
    int updateUser(Long userId, String username, String email);
}
