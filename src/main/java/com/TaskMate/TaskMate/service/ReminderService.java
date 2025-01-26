package com.TaskMate.TaskMate.service;

import com.TaskMate.TaskMate.controller.WebSocketController;
import com.TaskMate.TaskMate.dto.ReminderDTO;
import com.TaskMate.TaskMate.model.Reminder;
import com.TaskMate.TaskMate.model.Users;
import com.TaskMate.TaskMate.repo.ReminderRepository;
import com.TaskMate.TaskMate.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private  TaskService taskService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private WebSocketController webSocketController;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

    // Create a new reminder
    @Transactional
    public Reminder createReminder(ReminderDTO reminderDTO) {
        // Create Reminder entity from DTO
        Reminder reminder = new Reminder();
        reminder.setReminderTime(reminderDTO.getReminderTime());
        reminder.setMessage(reminderDTO.getMessage());
        reminder.setTask(taskService.getTaskById(reminderDTO.getTaskId()));

        // Fetch Users associated with the reminder
        Set<Users> users = new HashSet<>();
        for (Long userId : reminderDTO.getUserIds()) {
            Optional<Users> user = usersRepository.findById(userId);
            user.ifPresent(u -> {
                users.add(u);
                // Add the reminder to the user's reminders set to maintain bidirectional relationship
                u.getReminders().add(reminder);
            });
        }
        reminder.setUsers(users);

        // Save Reminder and return
        return reminderRepository.save(reminder);
    }

    // Update an existing reminder
    @Transactional
    public Reminder updateReminder(Long reminderId, ReminderDTO reminderDTO) {
        Optional<Reminder> reminderOptional = reminderRepository.findById(reminderId);

        if (reminderOptional.isEmpty()) {
            throw new RuntimeException("Reminder not found with id: " + reminderId);
        }

        Reminder reminder = reminderOptional.get();

        // Update reminder fields
        reminder.setReminderTime(reminderDTO.getReminderTime());
        reminder.setMessage(reminderDTO.getMessage());
        reminder.setTask(taskService.getTaskById(reminderDTO.getTaskId()));

        // Fetch Users associated with the reminder
        Set<Users> users = new HashSet<>();
        for (Long userId : reminderDTO.getUserIds()) {
            Optional<Users> user = usersRepository.findById(userId);
            user.ifPresent(users::add);
        }

        reminder.setUsers(users);

        // Save and return the updated reminder
        return reminderRepository.save(reminder);
    }

    // Get all reminders
    public List<Reminder> getAllReminders() {
        return reminderRepository.findAll();
    }

    // Get a reminder by ID
    public Reminder getReminderById(Long id) {
        return reminderRepository.findById(id).orElseThrow(() -> new RuntimeException("Reminder not found with id: " + id));
    }

    // Delete a reminder by ID
    public void deleteReminder(Long id) {
        reminderRepository.deleteById(id);
    }


    public void scheduleReminders() {
        // Every minute, check reminders and process them asynchronously
        List<Reminder> reminders = getAllReminders();

        int delayInSeconds = 0;
        for (Reminder reminder : reminders) {
            scheduler.schedule(() -> processReminder(reminder), delayInSeconds, TimeUnit.SECONDS);
            delayInSeconds += 5; // Increment delay for each subsequent reminder
        }
    }

    private void processReminder(Reminder reminder) {
        System.out.println("Processing reminder in thread: " + Thread.currentThread().getName());

        // Process reminder asynchronously (e.g., sending notifications)
        System.out.println("Processing reminder for Task: " + reminder.getTask().getTitle() +
                " | Message: " + reminder.getMessage());
        sendNotification(reminder);
    }

    private void sendNotification(Reminder reminder) {
        // Use WebSocketController to send the notification
        webSocketController.sendReminder(reminder);

        System.out.println("WebSocket notification sent for Task: " + reminder.getTask().getTitle() +
                " | Message: " + reminder.getMessage());
    }

    @Scheduled(fixedRate = 60000)
    public void checkReminders() {
        scheduleReminders();
    }
}
