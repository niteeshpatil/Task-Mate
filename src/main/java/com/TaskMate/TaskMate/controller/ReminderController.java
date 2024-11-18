package com.TaskMate.TaskMate.controller;

import com.TaskMate.TaskMate.dto.ReminderDTO;
import com.TaskMate.TaskMate.model.Reminder;
import com.TaskMate.TaskMate.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReminderController {

    @Autowired
    ReminderService reminderService;

    @PostMapping("/reminder")
    public Reminder reminder(@RequestBody ReminderDTO reminder){
        return  reminderService.createReminder(reminder);
    }
}
