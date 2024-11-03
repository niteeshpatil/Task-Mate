package com.TaskMate.TaskMate.controller;

import com.TaskMate.TaskMate.model.Users;
import com.TaskMate.TaskMate.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;





@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    @PostMapping("/register")
    public Users register(@RequestBody Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        return  usersService.register(user);
    }


}
