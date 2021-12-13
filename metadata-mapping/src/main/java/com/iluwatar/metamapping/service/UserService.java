package com.iluwatar.metamapping.service;

import com.iluwatar.metamapping.model.User;

import java.util.List;

public interface UserService {
    List<User> listUser();
    int createUser(User user);
    void updateUser(Integer id, User user);
    void deleteUser(Integer id);
    User getUser(Integer id);
    void close();
}
