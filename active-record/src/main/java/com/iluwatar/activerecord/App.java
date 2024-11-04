package com.iluwatar.activerecord;

import java.sql.SQLException;
import java.util.List;

public class App {

  public static void main(String[] args) {
    try {
      // Initialize the database and create the users table if it doesn't exist
      User.initializeTable();
      System.out.println("Database and table initialized.");

      // Create a new user and save it to the database
      User user1 = new User(null, "John Doe", "john.doe@example.com");
      user1.save();
      System.out.println("New user saved: " + user1.getName() + " with ID " + user1.getId());

      // Retrieve and display the user by ID
      User foundUser = User.findById(user1.getId());
      if (foundUser != null) {
        System.out.println("User found: " + foundUser.getName() + " with email " + foundUser.getEmail());
      } else {
        System.out.println("User not found.");
      }

      // Update the user’s details
      assert foundUser != null;
      foundUser.setName("John Updated");
      foundUser.setEmail("john.updated@example.com");
      foundUser.save();
      System.out.println("User updated: " + foundUser.getName() + " with email " + foundUser.getEmail());

      // Retrieve all users
      List<User> users = User.findAll();
      System.out.println("All users in the database:");
      for (User user : users) {
        System.out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Email: " + user.getEmail());
      }

      // Delete the user
      try {
        System.out.println("Deleting user with ID: " + foundUser.getId());
        foundUser.delete();
        System.out.println("User successfully deleted!");
      }
      catch (Exception e){
        System.out.println(e.getMessage());
      }

    } catch (SQLException e) {
      System.err.println("SQL error: " + e.getMessage());
    }
  }
}
