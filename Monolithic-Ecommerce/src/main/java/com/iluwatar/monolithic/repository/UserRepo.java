package com.iluwatar.monolithic.repository;

import com.iluwatar.monolithic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * This interface allows JpaRepository to generate queries for the required tables.
 */
public interface UserRepo extends JpaRepository<User, Long> {
  /**
   * Utilizes JpaRepository functionalities to generate a function which looks up in the User table using emails.
   * */
  User findByEmail(String email);
}