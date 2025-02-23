package com.TaskMate.TaskMate.controller;

import com.TaskMate.TaskMate.dto.ReminderDTO;
import com.TaskMate.TaskMate.model.Reminder;
import com.TaskMate.TaskMate.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReminderController {

    @Autowired
    ReminderService reminderService;

    //http://localhost:8080/reminder
    //    body
    //    {
    //        "reminderTime": "2025-01-20T10:30:00Z",
    //            "message":  <message>,
    //            "taskId": <taskId>,
    //            "userIds": [...<userIds>],
    //        "createdBy": <id>
    //    }
    @PostMapping("/reminder")
    public Reminder reminder(@RequestBody ReminderDTO reminder){
        return  reminderService.createReminder(reminder);
    }

    @GetMapping("/reminder")
    public List<Reminder> reminder(){
        return  reminderService.getAllReminders();
    }
}
