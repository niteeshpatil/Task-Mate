package com.TaskMate.TaskMate.service;
import com.TaskMate.TaskMate.model.Task;

import com.TaskMate.TaskMate.dto.UsersDTO;
import com.TaskMate.TaskMate.model.UserPrincipal;
import com.TaskMate.TaskMate.model.Reminder;
import com.TaskMate.TaskMate.model.Users;
import com.TaskMate.TaskMate.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;


import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsersService {

    @Autowired
    private AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JWTService jwtService;

    public Users register(Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        return  usersRepository.save(user);
    }

    public String verify(Users user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername())  ;
        } else {
            return "fail";
        }
    }

    public Users getUser(Long id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + id + " not found."));
    }

    public UsersDTO getUserData(Long id) {
        // Fetch the user entity by ID
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + id + " not found."));


        Set<Long> createdTaskIds = user.getCreatedTasks().stream()
                .map(Task::getId)
                .collect(Collectors.toSet());

        Set<Task> assignedTask = new HashSet<>(user.getAssignedTasks());
        Set<Reminder> reminders = new HashSet<>(user.getReminders());



        return new UsersDTO(
                user.getId(),
                user.getUsername(),
                createdTaskIds,
                assignedTask,
                reminders
        );
    }


    public Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserPrincipal) {
            return ((UserPrincipal) principal).getUserId();
        }
        return null; // No user authenticated
    }

}
