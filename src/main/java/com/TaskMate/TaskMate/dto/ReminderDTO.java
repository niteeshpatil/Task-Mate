package com.TaskMate.TaskMate.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ReminderDTO {
    private LocalDateTime reminderTime;
    private String message;
    private Long taskId;
    private List<Long> userIds; // List of User IDs (instead of a single userId)
    private Long createdBy;

    // Default constructor
    public ReminderDTO() {
    }

    // Parameterized constructor
    public ReminderDTO(LocalDateTime reminderTime, String message, Long taskId, List<Long> userIds, Long createdBy) {
        this.reminderTime = reminderTime;
        this.message = message;
        this.taskId = taskId;
        this.userIds = userIds;
        this.createdBy = createdBy;
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

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    // Override toString, equals, and hashCode for better object management

    @Override
    public String toString() {
        return "ReminderDTO{" +
                ", reminderTime=" + reminderTime +
                ", message='" + message + '\'' +
                ", taskId=" + taskId +
                ", userIds=" + userIds +
                ", createdBy=" + createdBy +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReminderDTO that = (ReminderDTO) o;

        if (!reminderTime.equals(that.reminderTime)) return false;
        if (!message.equals(that.message)) return false;
        if (!taskId.equals(that.taskId)) return false;
        if (!userIds.equals(that.userIds)) return false;
        return createdBy.equals(that.createdBy);
    }

}
