package com.example.taskflow_1.repository;

import com.example.taskflow_1.domain.User;
import com.example.taskflow_1.domain.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

    UserToken findByUser(User currentUser);
}
