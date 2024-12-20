package com.TaskMate.TaskMate.controller;

import com.TaskMate.TaskMate.dto.TaskDTO;
import com.TaskMate.TaskMate.model.Task;
import com.TaskMate.TaskMate.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping("/Create")
    public Task create(@RequestBody TaskDTO taskDTO) {
        return taskService.createTask(taskDTO);
    }
    @GetMapping("/Create")
    public List<Task> getTasks() {
        return taskService.getAllTasks();
    }
    @PutMapping("/Create/{taskId}")
    public Task updateTask(@PathVariable Long taskId,@RequestBody TaskDTO taskDTO) {
        return taskService.updateTask(taskId, taskDTO);
    }
}
