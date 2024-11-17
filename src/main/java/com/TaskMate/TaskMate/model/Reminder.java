package com.TaskMate.TaskMate.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime reminderTime;

    private String message;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false) // Ensures task is mandatory for reminder
    private Task task; // Task associated with this reminder

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // Ensures user is mandatory for reminder
    private Users user; // User receiving the reminder

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(LocalDateTime reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
