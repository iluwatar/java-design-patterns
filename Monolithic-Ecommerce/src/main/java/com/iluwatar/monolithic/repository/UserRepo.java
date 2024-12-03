package com.iluwatar.monolithic.repository;

import com.iluwatar.monolithic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
}