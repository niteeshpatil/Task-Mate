package com.TaskMate.TaskMate.dto;

import java.util.Set;

public class TaskDTO {

    private String title;
    private String description;
    private Long createdBy;
    private Set<Long> taskAssignees;  // List of User IDs assigned to this task
    private boolean completed;

    // Constructor
    public TaskDTO(String title, String description, Long createdBy, Set<Long> taskAssignees, Boolean completed) {
        this.title = title;
        this.description = description;
        this.createdBy = createdBy;
        this.taskAssignees = taskAssignees;
        this.completed = completed;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Set<Long> getTaskAssignees() {
        return taskAssignees;
    }

    public void setTaskAssignees(Set<Long> taskAssignees) {
        this.taskAssignees = taskAssignees;
    }

    public boolean getIsCompleted(){
        return completed;
    }
}
