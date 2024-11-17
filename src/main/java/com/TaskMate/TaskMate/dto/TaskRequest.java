package com.TaskMate.TaskMate.dto;

public class TaskRequest {

    private String title;
    private String description;
    private Long createdBy; // Assuming this refers to the user's ID

    // Constructor
    public TaskRequest(String title, String description, Long createdBy) {
        this.title = title;
        this.description = description;
        this.createdBy = createdBy;
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
}
