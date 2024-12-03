package com.iluwatar.monolithic.controller;

import com.iluwatar.monolithic.model.User;
import com.iluwatar.monolithic.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCon {
    @Autowired
    private UserRepo userRepository;

    public User registerUser(User user) {
        return userRepository.save(user);
    }
}
