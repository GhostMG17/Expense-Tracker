package com.example.expensetracker.service;

import com.example.expensetracker.entity.User;
import com.example.expensetracker.exception.BadRequestException;
import com.example.expensetracker.exception.NotFoundException;
import com.example.expensetracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User findById(Long id){
        return userRepository.findById(id).
                orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User createUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new BadRequestException("Email can not be null or empty.");
        }

        if (!user.getEmail().contains("@")) {
            throw new BadRequestException("Email should contain '@' symbol.");
        }

        if (userRepository.existsByEmailIgnoreCase(user.getEmail())) {
            throw new BadRequestException("A user with this email already exists.");
        }

        if (user.getPhone() == null || user.getPhone().isBlank()){
            throw new BadRequestException("Number cannot be null or empty.");
        }

        if (userRepository.existsByPhone(user.getPhone())) {
            throw new BadRequestException("A user with this phone already exists");
        }

        User newUser = new User(null, user.getFirstname(), user.getLastname(),
                user.getEmail(), user.getPhone());

        return userRepository.save(newUser);

    }


}
