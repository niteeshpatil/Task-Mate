package com.TaskMate.TaskMate.service;

import com.TaskMate.TaskMate.model.Users;
import com.TaskMate.TaskMate.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UserRepo userRepo;

    public Users register(Users user){
        return  userRepo.save(user);
    }
}
