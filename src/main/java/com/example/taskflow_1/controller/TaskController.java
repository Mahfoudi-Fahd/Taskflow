package com.example.taskflow_1.controller;

import com.example.taskflow_1.domain.Task;
import com.example.taskflow_1.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/save")
    public Task save(@RequestBody Task task) {
        return taskService.save(task);
    }

    @GetMapping("/all")
    public List<Task> findAll() {
        return taskService.findAll();
    }
}
