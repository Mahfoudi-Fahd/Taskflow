package com.example.taskflow_1.domain;

import com.example.taskflow_1.domain.enums.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "title most not be blank")
    private String title;

    @NotBlank
    private String description;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private TaskStatus taskStatus;

}
