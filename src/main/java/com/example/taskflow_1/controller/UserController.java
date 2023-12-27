package com.example.taskflow_1.controller;

import com.example.taskflow_1.domain.User;
import com.example.taskflow_1.dto.user.UserRequestDto;
import com.example.taskflow_1.dto.user.UserResponseDto;
import com.example.taskflow_1.response.ResponseMessage;
import com.example.taskflow_1.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
   private final UserService userService;

   @PostMapping ("/save")
    public ResponseEntity<ResponseMessage> save(@Valid @RequestBody UserRequestDto userRequestDto) {

       User user = userRequestDto.toUser();

         userService.save(user);
         UserResponseDto userResponseDto = UserResponseDto.fromUser(user);
         return ResponseMessage.created(userResponseDto, "User created successfully");
    }

    public User findByUsername(String username) {
        return userService.findByUsername(username);
    }

    public List<User> findAll() {
        return userService.findAll();
    }

    public void deleteById(Long id) {
        userService.deleteById(id);
    }
}
