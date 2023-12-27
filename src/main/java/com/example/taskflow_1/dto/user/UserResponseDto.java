package com.example.taskflow_1.dto.user;

import com.example.taskflow_1.domain.User;
import com.example.taskflow_1.domain.enums.UserRoles;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {

    private String username;

    private String email;

    private String password;



    private UserRoles role;


    public static UserResponseDto fromUser(User user) {
        return new UserResponseDto(
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }
}
