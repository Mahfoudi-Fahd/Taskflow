package com.example.taskflow_1.service.impl;

import com.example.taskflow_1.domain.Task;
import com.example.taskflow_1.domain.User;
import com.example.taskflow_1.repository.UserRepository;
import com.example.taskflow_1.repository.TaskRepository;
import com.example.taskflow_1.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {


    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    public Task save(Task task) {
        if (taskRepository.existsByTitle(task.getTitle())) {
            throw new IllegalArgumentException("A task with the same title already exists");
        }
        if (task.getStartDate().isAfter(task.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        if(task.getEndDate().isAfter(LocalDateTime.now().plusDays(3))){
            throw new IllegalArgumentException("End date cannot be more than 3 days from now");
        }
        return taskRepository.save(task);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }


    @Override
    public Task assignTaskToUser(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        task.setUser(user);

        return taskRepository.save(task);
    }


}
