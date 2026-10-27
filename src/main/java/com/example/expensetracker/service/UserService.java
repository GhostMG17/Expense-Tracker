package com.example.expensetracker.service;

import com.example.expensetracker.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();

    public List<User> getAllUsers() {
        return users;
    }

    public User findById(Long id){
        for (User user : users){
            if (user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }

    public User createUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email can not be null or empty.");
        }

        if (!user.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email should contain '@' symbol.");
        }

        boolean emailExists = false;

        for (User u : users) {
            if (user.getEmail().equalsIgnoreCase(u.getEmail())) {
                emailExists = true;
                break;
            }
        }

        if (emailExists) {
            throw new IllegalArgumentException("A user with this email already exists.");
        }

        User newUser = new User(users.size() + 1L, user.getFirstname(), user.getLastname(),
                user.getEmail(), user.getPhone());

        users.add(newUser);
        return newUser;

    }


}
