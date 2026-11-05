
package com.vasylyna.taskflowapi.dto;

import java.time.LocalDateTime;
import com.vasylyna.taskflowapi.dto.TaskResponse;
import com.vasylyna.taskflowapi.enums.TaskStatus;
import com.vasylyna.taskflowapi.enums.TaskPriority;

public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime createdAt;

    public TaskResponse(Long id, String title, String description,
                        TaskStatus status, TaskPriority priority,
                        LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
