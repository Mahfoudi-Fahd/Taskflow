package com.example.taskflow_1.dto.task;

import com.example.taskflow_1.domain.Tag;
import com.example.taskflow_1.domain.Task;
import com.example.taskflow_1.domain.User;
import com.example.taskflow_1.domain.enums.TaskStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;




@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskRequestDto {

    @NotBlank(message = "title is required")
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

    @NotNull(message = "tagIds Are required")
    private List<Long> tagIds;

    @NotNull(message = "createdBy is required")
    private Long createdBy;

public Task toTask() {
    Task task = Task.builder()
            .title(title)
            .description(description)
            .startDate(startDate)
            .endDate(endDate)
            .taskStatus(taskStatus)
            .createdBy(User.builder().id(createdBy).build())
            .build();

    List<Tag> tags = tagIds.stream()
            .map(tagId -> Tag.builder().id(tagId).build())
        .toList() ;

    task.setTags(tags);
    return task;

    }
}
