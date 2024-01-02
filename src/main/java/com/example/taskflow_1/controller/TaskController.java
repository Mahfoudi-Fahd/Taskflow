package com.example.taskflow_1.controller;

import com.example.taskflow_1.domain.Task;
import com.example.taskflow_1.domain.User;
import com.example.taskflow_1.dto.task.TaskRequestDto;
import com.example.taskflow_1.dto.task.TaskResponseDto;
import com.example.taskflow_1.response.ResponseMessage;
import com.example.taskflow_1.service.TaskService;
import com.example.taskflow_1.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;

    @PostMapping("/save-with-tags")
    public ResponseEntity<TaskResponseDto>saveWithTags(@Valid @RequestBody TaskRequestDto taskRequestDto) {

        Task task =   taskService.createTask(taskRequestDto.toTask(), taskRequestDto.getTagIds());
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

    @DeleteMapping("/delete/{taskId}/{userId}")
    public ResponseEntity<ResponseMessage> deleteById(@PathVariable Long taskId,@PathVariable Long userId) {

        Task selectedTask = taskService.findById(taskId);
        taskService.deleteById(selectedTask.getId(), userId);
        TaskResponseDto taskResponseDto = TaskResponseDto.fromTask(selectedTask);

        return ResponseMessage.ok(taskResponseDto,"Task deleted successfully");
    }

    @PostMapping("/{taskId}/replace")
    public ResponseEntity<ResponseMessage> replaceTask(@PathVariable Long taskId, @RequestParam Long userId) {
        // Retrieve the current user (userId)
        User currentUser = userService.findById(userId);

        Task replacedTask = taskService.replaceTask(taskId, currentUser);
    TaskResponseDto taskResponseDto = TaskResponseDto.fromTask(replacedTask);

        return ResponseMessage.ok( taskResponseDto, "Task replaced successfully");
    }
}


