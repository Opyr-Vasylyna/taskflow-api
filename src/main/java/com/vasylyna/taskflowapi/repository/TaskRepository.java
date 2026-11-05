
package com.vasylyna.taskflowapi.repository;

import com.vasylyna.taskflowapi.entity.Task;
import com.vasylyna.taskflowapi.enums.TaskStatus;
import com.vasylyna.taskflowapi.enums.TaskPriority;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByStatus(
            TaskStatus status,
            Pageable pageable
    );

    Page<Task> findByPriority(
            TaskPriority priority,
            Pageable pageable
    );

    Page<Task> findByStatusAndPriority(
            TaskStatus status,
            TaskPriority priority,
            Pageable pageable
    );
}
