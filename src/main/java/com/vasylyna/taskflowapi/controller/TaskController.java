
package com.vasylyna.taskflowapi.controller;

import com.vasylyna.taskflowapi.dto.TaskRequest;
import com.vasylyna.taskflowapi.dto.TaskResponse;
import com.vasylyna.taskflowapi.enums.TaskPriority;
import com.vasylyna.taskflowapi.enums.TaskStatus;
import com.vasylyna.taskflowapi.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@Tag(
        name = "Task Management",
        description = "Endpoints for creating, retrieving, updating and deleting tasks"
)
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(
            summary = "Get all tasks",
            description = "Retrieve tasks with optional status and priority filters, pagination and sorting"
    )
    @GetMapping
    public Page<TaskResponse> getAllTasks(
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(required = false) TaskPriority priority,
            @ParameterObject Pageable pageable) {

        return taskService.getTasks(status, priority, pageable);
    }

    @Operation(
            summary = "Get task by ID",
            description = "Retrieve detailed information about a specific task"
    )
    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        return taskService.getTaskResponseById(id);
    }

    @Operation(
            summary = "Create a new task",
            description = "Create a task with title, description, status and priority"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(@Valid @RequestBody TaskRequest request) {
        return taskService.createTask(request);
    }

    @Operation(
            summary = "Update an existing task",
            description = "Update the information of an existing task by its ID"
    )
    @PutMapping("/{id}")
    public TaskResponse updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequest request) {

        return taskService.updateTask(id, request);
    }

    @Operation(
            summary = "Delete a task by ID",
            description = "Permanently delete a task from the database"
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}
