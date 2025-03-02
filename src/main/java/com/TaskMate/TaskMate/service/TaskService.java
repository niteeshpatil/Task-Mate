package com.TaskMate.TaskMate.service;

import com.TaskMate.TaskMate.dto.TaskDTO;
import com.TaskMate.TaskMate.model.Task;
import com.TaskMate.TaskMate.model.Users;
import com.TaskMate.TaskMate.repo.TaskRepository;
import com.TaskMate.TaskMate.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UsersRepository userRepository;

    private final Sinks.Many<Task> taskUpdateSink = Sinks.many().multicast().onBackpressureBuffer();

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UsersRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Autowired
    UsersService usersService;

    @Transactional
    public Task createTask(TaskDTO taskDTO) {
        Logger logger = LoggerFactory.getLogger(TaskService.class);

        // Check if taskDTO is null
        if (taskDTO == null) {
            throw new IllegalArgumentException("TaskDTO cannot be null.");
        }

        // Fetch user who is creating the task
        Users user = usersService.getUser(taskDTO.getCreatedBy());

        // Check if user exists
        if (user == null) {
            logger.error("User with ID {} not found while creating task.", taskDTO.getCreatedBy());
            throw new IllegalArgumentException("User with ID " + taskDTO.getCreatedBy() + " not found.");
        }

        // Create and populate the task object
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(false);
        task.setCreatedBy(taskDTO.getCreatedBy());

        // Handle task assignees (users assigned to the task)
        Set<Users> assignees = new HashSet<>();
        for (Long userId : taskDTO.getTaskAssignees()) {
            Optional<Users> assignee= usersRepository.findById(userId); // Assuming you have a method to get a user by ID
            if (assignee.isPresent()) {
                assignees.add(assignee.get());
            } else {
                logger.warn("User with ID {} not found while assigning to task.", userId);
            }
        }
        // Set assignees for the task
        task.setAssignees(assignees);
        Task savedTask = taskRepository.save(task);

        // Emit real-time update
        taskUpdateSink.tryEmitNext(savedTask);

        return savedTask;
    }


    @Transactional
    public Task updateTask(Long taskId, TaskDTO taskDTO) {
        Logger logger = LoggerFactory.getLogger(TaskService.class);
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty()) {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found.");
        }

        // Check if the current user is authorized to modify the task
        if (!taskOptional.get().getCreatedBy().equals(usersService.getCurrentUserId())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to modify this task.");
        }

        Task task = taskOptional.get();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(false); // You can also add the completed flag in TaskDTO if required

        // Handle task assignees (users assigned to the task)
        Set<Users> updatedAssignees = new HashSet<>(task.getAssignees()); // Get current assignees

        // Add new users to the task assignees
        for (Long userId : taskDTO.getTaskAssignees()) {
            Optional<Users> assignee = usersRepository.findById(userId);
            if (assignee.isPresent()) {
                updatedAssignees.add(assignee.get());
            } else {
                logger.warn("User with ID {} not found while assigning to task.", userId);
            }
        }

        // Update assignees for the task
        task.setAssignees(updatedAssignees);

        Task updatedTask = taskRepository.save(task);

        // Emit real-time update
        taskUpdateSink.tryEmitNext(updatedTask);

        return updatedTask;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new IllegalArgumentException("Task with ID " + taskId + " not found.");
        }
        taskRepository.deleteById(taskId);
    }

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

    public Flux<Task> getTaskUpdates() {
        return taskUpdateSink.asFlux();
    }
}
