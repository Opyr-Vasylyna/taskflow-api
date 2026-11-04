
package com.vasylyna.taskflowapi.repository;

import com.vasylyna.taskflowapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
