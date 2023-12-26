package com.example.taskflow_1.dto.task;

import com.example.taskflow_1.domain.Task;
import com.example.taskflow_1.domain.enums.TaskStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskRequestDto {

    @NotBlank(message = "title most not be blank")
    private String title;

    @NotBlank(message = "description is required")
    private String description;

    @NotNull(message = "startDate is required")
    @FutureOrPresent(message = "startDate must be in the present or in the future")
    private LocalDateTime startDate;

    @NotNull(message = "endDate is required")
    @FutureOrPresent(message = "endDate must be in the present or in the future")
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "taskStatus is required")
    private TaskStatus taskStatus;


public Task toTask() {
    return Task.builder()
            .title(title)
            .description(description)
            .startDate(startDate)
            .endDate(endDate)
            .taskStatus(taskStatus)
            .build();

    }
}
