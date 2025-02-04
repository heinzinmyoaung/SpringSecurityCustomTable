package com.example.springSecurity.repository;

import com.example.springSecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByUserName(String username);

}
