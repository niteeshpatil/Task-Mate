package com.TaskMate.TaskMate.repo;

import com.TaskMate.TaskMate.model.Reminder;
import com.TaskMate.TaskMate.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {
}
