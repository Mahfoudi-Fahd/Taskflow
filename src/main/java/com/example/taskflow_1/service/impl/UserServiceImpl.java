package com.example.taskflow_1.service.impl;

import com.example.taskflow_1.domain.User;
import com.example.taskflow_1.domain.enums.UserRoles;
import com.example.taskflow_1.repository.UserRepository;
import com.example.taskflow_1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

   private final UserRepository userRepository;
    @Override
    public User save(User user) {
        if(userRepository.existsByUsername(user.getUsername())){
            throw new IllegalArgumentException("A user with the same username already exists");
        }
        if(userRepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("A user with the same email already exists");
        }
        user.setRole(UserRoles.valueOf("USER"));
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}
