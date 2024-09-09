package com.example.springSecurity.service.Impl;

import com.example.springSecurity.entity.Role;
import com.example.springSecurity.entity.User;
import com.example.springSecurity.entity.UserPrincipal;
import com.example.springSecurity.repository.RoleRepo;
import com.example.springSecurity.repository.UserRepo;
import com.example.springSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new UserPrincipal(user);
    }

    @Override
    public void registerAcc(User user) {

//    Way1
        Set<Role> roles = new HashSet<>();

        for(Role role: user.getRoles()){
            Role existingRole = roleRepo.findByName(role.getName());

            roles.add(existingRole);
        }
        user.setRoles(roles);
        userRepo.save(user);

//    Way2
        // Update the roles directly in the user object
//        user.setRoles(
//                user.getRoles().stream()
//                        .map(role -> roleRepo.findByName(role.getName()))  // Fetch the existing role by name
//                        .collect(Collectors.toSet())  // Collect as a Set
//        );
//        userRepo.save(user);
    }
}


