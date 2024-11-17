package com.TaskMate.TaskMate.controller;

import com.TaskMate.TaskMate.dto.TaskRequest;
import com.TaskMate.TaskMate.model.Task;
import com.TaskMate.TaskMate.model.Users;
import com.TaskMate.TaskMate.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/Create")
    public Task create(@RequestBody TaskRequest taskRequest) {
        return taskService.createTask(taskRequest);
    }
    @GetMapping("/Create")
    public List<Task> getTasks() {
        return taskService.getAllTasks();
    }
    @PutMapping("/Create/{taskId}")
    public Task updateTask(@PathVariable Long taskId,@RequestBody TaskRequest taskRequest) {
        return taskService.updateTask(taskId,taskRequest);
    }
}
