package com.TaskMate.TaskMate.dto;

import com.TaskMate.TaskMate.model.Reminder;
import com.TaskMate.TaskMate.model.Task;

import java.util.Set;

public class UsersDTO {

    private Long id;
    private String username;
    private Set<Long> createdTasks; // IDs of tasks created by the user
    private Set<Task> assignedTasks; // IDs of tasks assigned to the user
    private Set<Reminder> reminders; // IDs of reminders associated with the user

    // Constructors
    public UsersDTO() {}

    public UsersDTO(Long id, String username, Set<Long> createdTasks, Set<Task> assignedTasks, Set<Reminder> reminders) {
        this.id = id;
        this.username = username;
        this.createdTasks = createdTasks;
        this.assignedTasks = assignedTasks;
        this.reminders = reminders;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Long> getCreatedTasks() {
        return createdTasks;
    }

    public void setCreatedTasks(Set<Long> createdTasks) {
        this.createdTasks = createdTasks;
    }

    public Set<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public void setAssignedTasks(Set<Task> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

    public Set<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(Set<Reminder> reminders) {
        this.reminders = reminders;
    }
}
