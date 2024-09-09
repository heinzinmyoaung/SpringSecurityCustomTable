package com.example.springSecurity.controller;

import com.example.springSecurity.entity.User;
import com.example.springSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {
    @Autowired
    UserService userService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @GetMapping("/")
    public String home(){
        return "Home Page";
    }

    @PostMapping("/create")
    public String createTest(){
        return "Create";
    }

    @PutMapping("/update/{id}")
    public String updateTest(@RequestParam ("id") int id){
        return "Update";
    }

    @DeleteMapping("/delete")
    public String deleteTest(){
        return "Delete";
    }

    // Account Register
    @PostMapping("/register")
    public void registerAcc(@RequestBody User user){
        user.setPassword(encoder.encode(user.getPassword()));
        userService.registerAcc(user);
    }
}






