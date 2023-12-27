package com.example.taskflow_1.service;

import com.example.taskflow_1.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    User save(User user);
    User findByUsername(String username);
    List<User> findAll();
    void deleteById(Long id);

}
