package com.TaskMate.TaskMate.repo;

import com.TaskMate.TaskMate.model.Reminder;
import com.TaskMate.TaskMate.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    List<Reminder> findAllByReminderTimeBefore(LocalDateTime time);
}
