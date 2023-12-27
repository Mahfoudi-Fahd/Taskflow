package com.example.taskflow_1.repository;

import com.example.taskflow_1.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}