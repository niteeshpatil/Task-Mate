package com.TaskMate.TaskMate.controller;

import com.TaskMate.TaskMate.dto.TaskDTO;
import com.TaskMate.TaskMate.model.Task;
import com.TaskMate.TaskMate.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    TaskService taskService;

    //http://localhost:8080/Create
    //    body
    //    {
    //        "title": <title>,
    //            "description": <description>
    //            "createdBy": <id>,
    //            "completed": <completed>,
    //            "taskAssignees": [<id1>,<id2>]
    //    }
    @PostMapping("/Create")
    public Task create(@RequestBody TaskDTO taskDTO) {
        return taskService.createTask(taskDTO);
    }
    //http://localhost:8080/Create
    @GetMapping("/Create")
    public List<Task> getTasks() {
        return taskService.getAllTasks();
    }

    //http://localhost:8080/Create/<taskId>
    //    body
    //    {
    //        "id": <id>,
    //            "title": <title>,
    //            "description": <description>,
    //            "completed": <completed>,
    //            "createdBy": <id>,
    //            "taskAssignees": [<id1>,<id2>]
    //    }
    @PutMapping("/Create/{taskId}")
    public Task updateTask(@PathVariable Long taskId,@RequestBody TaskDTO taskDTO) {
        return taskService.updateTask(taskId, taskDTO);
    }

    //curl -v -N -u username:password http://localhost:8080/updates
    @GetMapping(value = "/updates", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Task> streamTaskUpdates() {
        return taskService.getTaskUpdates();
    }
}
