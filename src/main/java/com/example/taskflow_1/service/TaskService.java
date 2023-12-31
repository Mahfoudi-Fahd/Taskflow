package com.example.taskflow_1.service;

import com.example.taskflow_1.domain.Task;
import com.example.taskflow_1.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

     Task createTask(Task task, List<Long> tagIds);

     List<Task> findAll();

     Task assignTaskToUser(Long taskId, Long userId, Long assignerId);

     Task markTaskAsDone(Task task);

     Task markTaskAsInProgress(Task task);

     Task findById(Long taskId);

     void  deleteById(Long taskId, Long userId);

     Task replaceTask(Long taskId, User currentUser);
}
