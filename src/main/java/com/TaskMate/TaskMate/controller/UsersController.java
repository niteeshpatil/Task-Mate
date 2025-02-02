package com.TaskMate.TaskMate.controller;

import com.TaskMate.TaskMate.dto.UsersDTO;
import com.TaskMate.TaskMate.model.Users;
import com.TaskMate.TaskMate.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;
    //http://localhost:8080/register
    //body
    // {
    //        "username": <username>,
    //        "password": <password>
    //  }
    @PostMapping("/register")
    public Users register(@RequestBody Users user) {
        return usersService.register(user);

    }
    //http://localhost:8080/login
    //body
    // {
    //        "username": <username>,
    //        "password": <password>
    //  }
    @PostMapping("/login")
    public String login(@RequestBody Users user) {

        return usersService.verify(user);
    }

    //http://localhost:8080/getUserData/<id>
    @GetMapping("/getUserData/{id}")
    public UsersDTO getUserData(@PathVariable Long id) {
        return usersService.getUserData(id);
    }
}
