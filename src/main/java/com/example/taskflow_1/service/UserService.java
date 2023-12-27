package com.example.taskflow_1.service;

import com.example.taskflow_1.domain.User;

import java.util.List;

public interface UserService {
    User save(User user);
    User findByUsername(String username);
    List<User> findAll();
    void deleteById(Long id);

}
