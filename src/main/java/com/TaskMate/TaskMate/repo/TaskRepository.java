package com.TaskMate.TaskMate.repo;

import com.TaskMate.TaskMate.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long>{

}