package com.TaskMate.TaskMate.service;

import com.TaskMate.TaskMate.model.UserPrincipal;
import com.TaskMate.TaskMate.model.Users;
import com.TaskMate.TaskMate.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JWTService jwtService;

    public Users register(Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        return  userRepo.save(user);
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
        return userRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with ID " + id + " not found."));
    }

    public Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserPrincipal) {
            return ((UserPrincipal) principal).getUserId();
        }
        return null; // No user authenticated
    }


}
