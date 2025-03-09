package com.TaskMate.TaskMate.service;

import com.TaskMate.TaskMate.controller.WebSocketController;
import com.TaskMate.TaskMate.dto.ReminderDTO;
import com.TaskMate.TaskMate.model.Reminder;
import com.TaskMate.TaskMate.model.Users;
import com.TaskMate.TaskMate.repo.ReminderRepository;
import com.TaskMate.TaskMate.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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
    private ThreadPoolTaskExecutor customTaskExecutor;

    @Autowired
    private WebSocketController webSocketController;

    // Create a new reminder
    @Transactional
    public Reminder createReminder(ReminderDTO reminderDTO) {
        try {
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
        } catch (Exception e) {
            // Log the exception (optional)
            System.out.println("Error creating reminder: "+ e);
            // Rethrow a custom exception or return null if needed
            throw new RuntimeException("Error creating reminder. Please try again later.", e);
        }
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
        List<Reminder> reminders = reminderRepository.findAll();
        int delayInSeconds = 0;

        for (Reminder reminder : reminders) {
            final int finalDelayInSeconds = delayInSeconds;
            // Schedule each reminder with an increasing delay
            customTaskExecutor.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(finalDelayInSeconds);
                    processReminder(reminder);  // Process reminder after the delay
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            delayInSeconds += 5; // Increment delay for the next reminder
        }
    }

    private void processReminder(Reminder reminder) {
        String message = GetNotification(reminder);
        System.out.println("Thread Used : " + Thread.currentThread().getName()+"\n"+message);
        webSocketController.sendReminder(message);
    }

    @Scheduled(fixedRate = 60000)
    public void checkReminders() {
        scheduleReminders();
    }

    private String GetNotification(Reminder reminder){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a dd/MM/yyyy");
        String formattedTime = reminder.getReminderTime().format(formatter);
        return "Reminder: " + reminder.getMessage() +
                ", Task: " + reminder.getTask().getTitle() +
                ", Time: " + formattedTime;
    }
}
