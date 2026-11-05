
package com.vasylyna.taskflowapi.service;

import com.vasylyna.taskflowapi.dto.TaskResponse;
import com.vasylyna.taskflowapi.entity.Task;
import com.vasylyna.taskflowapi.repository.TaskRepository;
import org.springframework.stereotype.Service;
import com.vasylyna.taskflowapi.dto.TaskRequest;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public TaskResponse getTaskResponseById(Long id) {
        Task task = getTaskById(id);
        return toResponse(task);
    }


    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "Task not found with id: " + id
                        ));
    }


    public TaskResponse createTask(TaskRequest request) {
        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());

        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }

        if (request.getPriority() != null) {
            task.setPriority(request.getPriority());
        }

        Task savedTask = taskRepository.save(task);

        return toResponse(savedTask);
    }




    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task existingTask = getTaskById(id);

        existingTask.setTitle(request.getTitle());
        existingTask.setDescription(request.getDescription());

        if (request.getStatus() != null) {
            existingTask.setStatus(request.getStatus());
        }

        if (request.getPriority() != null) {
            existingTask.setPriority(request.getPriority());
        }

        Task savedTask = taskRepository.save(existingTask);

        return toResponse(savedTask);
    }


    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getCreatedAt()
        );
    }

}
