package com.example.springSecurity.service;

import com.example.springSecurity.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void registerAcc (User user);
}
