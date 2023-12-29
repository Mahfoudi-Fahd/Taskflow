package com.example.taskflow_1.controller;

import com.example.taskflow_1.domain.Task;
import com.example.taskflow_1.dto.task.TaskRequestDto;
import com.example.taskflow_1.dto.task.TaskResponseDto;
import com.example.taskflow_1.response.ResponseMessage;
import com.example.taskflow_1.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/save-with-tags")
    public ResponseEntity<TaskResponseDto>saveWithTags(@Valid @RequestBody TaskRequestDto taskRequestDto) {

        Task task =   taskService.createTaskWithTags(taskRequestDto.toTask(), taskRequestDto.getTagIds());
        TaskResponseDto taskResponseDto = TaskResponseDto.fromTask(task);

        return new ResponseEntity<>(taskResponseDto, HttpStatus.CREATED);
    }


    @GetMapping("/all")
    public List<Task> findAll() {
        return taskService.findAll();
    }


   @PutMapping("/assign/{taskId}/{userId}/{assignerId}")
    public ResponseEntity<ResponseMessage> assignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId, @PathVariable Long assignerId) {
        Task task = taskService.assignTaskToUser(taskId, userId, assignerId);
        TaskResponseDto taskResponseDto = TaskResponseDto.fromTask(task);
        return ResponseMessage.ok(taskResponseDto, "Task assigned successfully");
    }

    @PutMapping("/mark-as-done/{taskId}")
    public ResponseEntity<ResponseMessage> markTaskAsDone(@PathVariable Long taskId) {

        Task selectedTask = taskService.findById(taskId);
        Task task = taskService.markTaskAsDone(selectedTask);
        TaskResponseDto taskResponseDto = TaskResponseDto.fromTask(task);
        return ResponseMessage.ok(taskResponseDto, "Task marked as done successfully");
    }

    @PutMapping("/mark-as-in-progress/{taskId}")
    public ResponseEntity<ResponseMessage> markTaskAsInProgress(@PathVariable Long taskId) {


        Task selectedTask = taskService.findById(taskId);
        Task task = taskService.markTaskAsInProgress(selectedTask);
        TaskResponseDto taskResponseDto = TaskResponseDto.fromTask(task);
        return ResponseMessage.ok(taskResponseDto, "Task marked as In Progress successfully");
    }
}


