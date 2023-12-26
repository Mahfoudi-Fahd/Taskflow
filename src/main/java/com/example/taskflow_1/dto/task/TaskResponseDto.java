package com.example.taskflow_1.dto.task;

import com.example.taskflow_1.domain.Task;
import com.example.taskflow_1.domain.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskResponseDto {

    private String title;


    private String description;


    private LocalDateTime startDate;


    private LocalDateTime endDate;


    private TaskStatus taskStatus;


    public static TaskResponseDto fromTask(Task task) {
        return new TaskResponseDto(
                task.getTitle(),
                task.getDescription(),
                task.getStartDate(),
                task.getEndDate(),
                task.getTaskStatus()
        );
    }
}
