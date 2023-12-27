package com.example.taskflow_1.service;

import com.example.taskflow_1.domain.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
     Task save(Task task);
     List<Task> findAll();
     Task assignTaskToUser(Long taskId, Long userId);
}
