package com.iluwatar.monolithic.controller;

import com.iluwatar.monolithic.model.User;
import com.iluwatar.monolithic.repository.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class UserCon {
    private final UserRepo userRepository;

  public UserCon(UserRepo userRepository) {
    this.userRepository = userRepository;
  }

  public User registerUser(User user) {
        return userRepository.save(user);
    }
}
