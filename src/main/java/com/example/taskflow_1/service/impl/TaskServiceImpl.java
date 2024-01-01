package com.example.taskflow_1.service.impl;

import com.example.taskflow_1.domain.Tag;
import com.example.taskflow_1.domain.Task;
import com.example.taskflow_1.domain.User;
import com.example.taskflow_1.domain.UserToken;
import com.example.taskflow_1.domain.enums.TaskStatus;
import com.example.taskflow_1.domain.enums.UserRoles;
import com.example.taskflow_1.repository.TagRepository;
import com.example.taskflow_1.repository.UserRepository;
import com.example.taskflow_1.repository.TaskRepository;
import com.example.taskflow_1.repository.UserTokenRepository;
import com.example.taskflow_1.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {


    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final UserTokenRepository userTokenRepository;


    @Override
    public Task createTaskWithTags(Task task, List<Long> tagIds) {
        if (taskRepository.existsByTitle(task.getTitle())) {
            throw new IllegalArgumentException("A task with the same title already exists");
        }
        if (task.getStartDate().isAfter(task.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        if (task.getEndDate().isAfter(LocalDateTime.now().plusDays(3))) {
            throw new IllegalArgumentException("End date cannot be more than 3 days from now");
        }

        List<Tag> tags = tagRepository.findAllById(tagIds);
        if (tags.size() != tagIds.size()) {
            throw new IllegalArgumentException("One or more tags do not exist");
        }
        // Ensure all fetched tags are managed in the current persistence context
        List<Tag> managedTags = new ArrayList<>();
        for (Tag tag : tags) {
            managedTags.add(tagRepository.findById(tag.getId()).orElse(null));
        }

        task.setTags(managedTags); // Set the list of managed tags to the task
        task.setIsReplaced(false);
        task.setAssignedBy(task.getCreatedBy());
        return taskRepository.save(task);
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }


    @Override
    public Task assignTaskToUser(Long taskId, Long userId, Long assignerId) {

        User assigner = userRepository.findById(assignerId).orElseThrow(() -> new IllegalArgumentException("Assigner not found"));

        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Check if the task status is done
        if (task.getTaskStatus() == TaskStatus.DONE) {
            throw new IllegalArgumentException("You cannot assign a task that is already done");
        }

        //check if the task is passed the end date
        if (task.getEndDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("You cannot assign a task that has passed the end date");
        }

        //check if the User is an admin
        if (assigner.getRole() == UserRoles.ADMIN) {
            task.setAssignedBy(assigner);
            task.setUser(user);

            //check if the User is a user
        } else if (assigner.getRole() == UserRoles.USER) {

            //check if the task is assigned to another user
            if (task.getUser() != null) {
                throw new IllegalArgumentException("You cannot assign a task that is already assigned to another user");
            }

            //check if the assigner is the same as the user
            if (Objects.equals(assigner.getId(), user.getId())) {
                task.setAssignedBy(assigner);
                task.setUser(user);
            } else {
                throw new IllegalArgumentException("You cannot assign a task to another user");
            }
        }


        return taskRepository.save(task);
    }

    @Override
    public Task markTaskAsDone(Task task) {

        if (task.getEndDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("You cannot mark a task as done after the end date");
        }

        task.setTaskStatus(TaskStatus.DONE);

        taskRepository.save(task);

        return task;
    }

    @Override
    public Task markTaskAsInProgress(Task task) {

        if (task.getEndDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("You cannot mark a task as In Progress after the end date");
        }

        task.setTaskStatus(TaskStatus.IN_PROGRESS);

        return taskRepository.save(task);
    }

    @Override
    public Task findById(Long taskId) {
      return   taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }

    @Override
    public void  deleteById(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        UserToken userToken = userTokenRepository.findByUser(user);

        if (user.getRole() == UserRoles.USER) {
            if (userToken.getMonthlyDeletionTokens() <= 0) {
                throw new IllegalArgumentException("You do not have sufficient deletion tokens");
            }

            if (!Objects.equals(task.getCreatedBy().getId(), user.getId())) {
                throw new IllegalArgumentException("You cannot delete a task that you did not create");
            }

            // Decrement deletion tokens for users with the USER role
            userToken.setMonthlyDeletionTokens(userToken.getMonthlyDeletionTokens() - 1);
            userRepository.save(user);
        }

        if (task.getTaskStatus() == TaskStatus.DONE) {
            throw new IllegalArgumentException("You cannot delete a task that is already done");
        }
        if (task.getEndDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("You cannot delete a task that has passed the end date");
        }

        taskRepository.deleteById(taskId);
    }

    @Override
    public Task replaceTask(Long taskId, User currentUser) {
    Task task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        if (task == null) {
            throw new IllegalArgumentException("The specified task does not exist.");
        }

        assert task.getUser() != null;
        if (!task.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("You can only replace tasks assigned to you.");
        }

        UserToken userToken = userTokenRepository.findByUser(currentUser);
        if (userToken == null || userToken.getDailyReplacementTokens() <= 0) {
            throw new IllegalArgumentException("You don't have enough daily task replacement tokens.");
        }

        // Decrement the daily replacement tokens after successfully replacing the task
        userToken.setDailyReplacementTokens(userToken.getDailyReplacementTokens() - 1);
        userTokenRepository.save(userToken);

        task.setIsReplaced(true);
        task.setUser(null);
        task.setAssignedBy(null);
        task.setTaskStatus(TaskStatus.TODO);
        return taskRepository.save(task);
    }
}
