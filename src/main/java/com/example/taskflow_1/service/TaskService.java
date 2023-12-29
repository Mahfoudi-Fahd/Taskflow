package com.example.taskflow_1.service;

import com.example.taskflow_1.domain.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

     Task createTaskWithTags(Task task, List<Long> tagIds);

     List<Task> findAll();

     Task assignTaskToUser(Long taskId, Long userId, Long assignerId);

     Task markTaskAsDone(Task task);

     Task markTaskAsInProgress(Task task);

     Task findById(Long taskId);
}
