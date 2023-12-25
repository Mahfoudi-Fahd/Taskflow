package com.example.taskflow_1.controller;

import com.example.taskflow_1.domain.Task;
import com.example.taskflow_1.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/save")
    public ResponseEntity<Task> save(@Valid @RequestBody Task task) {
        return ResponseEntity.ok(taskService.save(task));
    }

    @GetMapping("/all")
    public List<Task> findAll() {
        return taskService.findAll();
    }
}
