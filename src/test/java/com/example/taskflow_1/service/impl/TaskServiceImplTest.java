package com.example.taskflow_1.service.impl;

import com.example.taskflow_1.domain.Tag;
import com.example.taskflow_1.domain.Task;
import com.example.taskflow_1.domain.User;
import com.example.taskflow_1.repository.TagRepository;
import com.example.taskflow_1.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @Mock
    TaskRepository taskRepository ;
    @Mock
    TagRepository tagRepository;

    @Mock
    TaskServiceImpl taskServiceImpl ;


    @Test
     void test_createTask_validInputData() {
        // Arrange
        Task task = new Task();
        task.setTitle("New Task");
        task.setDescription("Description");
        task.setStartDate(LocalDateTime.now());
        task.setEndDate(LocalDateTime.now().plusDays(2));
        task.setCreatedBy(new User());
        List<Long> tagIds = new ArrayList<>();
        tagIds.add(1L);
        tagIds.add(2L);

        // Mocking
        when(taskRepository.existsByTitle(anyString())).thenReturn(false);
        when(tagRepository.findAllById(anyList())).thenReturn(Arrays.asList(new Tag(), new Tag()));

        // Act
        Task result = taskServiceImpl.createTask(task, tagIds);

        // Assert
        assertNotNull(result);
        assertFalse(result.getIsReplaced());
        assertEquals(result.getAssignedBy(), result.getCreatedBy());
        assertEquals(result.getTags().size(), 2);
        verify(taskRepository, times(1)).save(task);
    }
}