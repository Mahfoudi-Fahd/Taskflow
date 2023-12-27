package com.example.taskflow_1.controller;

import com.example.taskflow_1.domain.Task;
import com.example.taskflow_1.dto.task.TaskRequestDto;
import com.example.taskflow_1.dto.task.TaskResponseDto;
import com.example.taskflow_1.response.ResponseMessage;
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
    public ResponseEntity<ResponseMessage> save(@Valid @RequestBody TaskRequestDto taskRequestDto) {

        Task task = taskRequestDto.toTask();
        taskService.save(task);
        TaskResponseDto taskResponseDto = TaskResponseDto.fromTask(task);

        return ResponseMessage.created(taskResponseDto, "Task created successfully");
    }

    @GetMapping("/all")
    public List<Task> findAll() {
        return taskService.findAll();
    }


   @PutMapping("/assign/{taskId}/{userId}")
    public ResponseEntity<ResponseMessage> assignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId) {
        Task task = taskService.assignTaskToUser(taskId, userId);
        TaskResponseDto taskResponseDto = TaskResponseDto.fromTask(task);
        return ResponseMessage.ok(taskResponseDto, "Task assigned successfully");
    }
}


