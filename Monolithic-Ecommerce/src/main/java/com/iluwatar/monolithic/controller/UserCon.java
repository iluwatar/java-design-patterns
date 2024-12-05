package com.iluwatar.monolithic.controller;

import com.iluwatar.monolithic.model.User;
import com.iluwatar.monolithic.repository.UserRepo;
import org.springframework.stereotype.Service;
/**
 * UserCon is a controller class for managing user operations.
 */
@Service
public class UserCon {
  private final UserRepo userRepository;
  /**
 * Linking Controller to DB.
 */
  public UserCon(UserRepo userRepository) {
    this.userRepository = userRepository;
  }
  /**
  * Adds a user to the DB.
  */
  public User registerUser(User user) {
    return userRepository.save(user);
  }
}
