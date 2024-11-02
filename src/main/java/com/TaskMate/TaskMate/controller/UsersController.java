package com.TaskMate.TaskMate.controller;

import com.TaskMate.TaskMate.model.Users;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;





@RestController
public class UsersController {


    @GetMapping("/User")
    public List<Users> getUsers(){
     return null;
    }

    @GetMapping("/Csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest req){
      return(CsrfToken) req.getAttribute("_csrf");
    }

    @PostMapping("/User")
    public Users addUser(@RequestBody Users user){
        return user;
    }


}
