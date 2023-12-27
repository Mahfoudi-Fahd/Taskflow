package com.example.taskflow_1.domain;

import com.example.taskflow_1.domain.enums.TaskStatus;
import jakarta.persistence.*;
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

    private String title;


    private String description;


    private LocalDateTime startDate;


    private LocalDateTime endDate;


    private TaskStatus taskStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
