package com.TaskMate.TaskMate.service;

import com.TaskMate.TaskMate.dto.TaskRequest;
import com.TaskMate.TaskMate.model.Task;
import com.TaskMate.TaskMate.model.Users;
import com.TaskMate.TaskMate.repo.TaskRepository;
import com.TaskMate.TaskMate.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.server.ResponseStatusException;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepo userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepo userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Autowired
    UsersService usersService;

    @Transactional
    public Task createTask(TaskRequest taskRequest) {
        Logger logger = LoggerFactory.getLogger(TaskService.class);

        try {
            // Check if taskRequest is null
            if (taskRequest == null) {
                throw new IllegalArgumentException("TaskRequest cannot be null.");
            }

            // Fetch user from repository
            Users user = usersService.getUser(taskRequest.getCreatedBy());

            // Check if user exists
            if (user ==null) {
                logger.error("User with ID {} not found while creating task.", taskRequest.getCreatedBy());
                throw new IllegalArgumentException("User with ID " + taskRequest.getCreatedBy() + " not found.");
            }

            // Create and populate the task object
            Task task = new Task();
            task.setTitle(taskRequest.getTitle());
            task.setDescription(taskRequest.getDescription());
            task.setCompleted(false);
            task.setCreatedBy(taskRequest.getCreatedBy());

            // Save the task and return it
            return taskRepository.save(task);

        } catch (IllegalArgumentException ex) {
            // Log and rethrow argument-related errors
            logger.error("Validation error while creating task: {}", ex.getMessage(), ex);
            throw ex; // Re-throw the specific error

        } catch (DataAccessException ex) {
            // Handle database-related exceptions
            logger.error("Database error while creating task: {}", ex.getMessage(), ex);
            throw new RuntimeException("Database error occurred while creating task.", ex);

        } catch (Exception ex) {
            // Catch all other unexpected errors
            logger.error("Unexpected error while creating task: {}", ex.getMessage(), ex);
            throw new RuntimeException("Unexpected error occurred while creating task.", ex);
        }
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Transactional
    public Task updateTask(Long taskId, TaskRequest taskRequest) {

        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found.");
        }
        System.out.println(usersService.getCurrentUserId()+" "+taskOptional.get().getCreatedBy());
        if (!taskOptional.get().getCreatedBy().equals(usersService.getCurrentUserId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to modify this task.");
        }

        Task task = taskOptional.get();
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setCompleted(false); // You can also add the completed flag in TaskRequest if required
        return taskRepository.save(task);
    }

    /**
     * Delete a task by ID.
     *
     * @param taskId the ID of the task to delete
     */
    @Transactional
    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found.");
        }
        taskRepository.deleteById(taskId);
    }

    /**
     * Assign users to a task.
     *
     * @param taskId      the ID of the task
     * @param assigneeIds the IDs of users to assign
     * @return the updated Task
     */
    @Transactional
    public Task assignUsersToTask(Long taskId, List<Long> assigneeIds) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found.");
        }

        Task task = taskOptional.get();
        List<Users> assignees = userRepository.findAllById(assigneeIds);
        task.getAssignees().addAll(assignees);
        return taskRepository.save(task);
    }
}
