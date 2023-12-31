package com.example.taskflow_1.repository;

import com.example.taskflow_1.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    boolean existsByTitle(String title);
}
