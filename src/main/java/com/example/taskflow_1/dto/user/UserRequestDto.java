package com.example.taskflow_1.dto.user;

import com.example.taskflow_1.domain.User;
import com.example.taskflow_1.domain.enums.UserRoles;
import com.example.taskflow_1.dto.task.TaskRequestDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "email is required")
    private String email;

    @NotBlank(message = "password is required")
    private String password;


//    private UserRoles role;


   public User toUser() {
        return User.builder()
                .username(username)
                .email(email)
                .password(password)
//                .role(role)
                .build();
    }


}
