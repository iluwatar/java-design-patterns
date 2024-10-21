package com.iluwatar.activerecord;

public class App {

  public App(){
    //create a temporary table
    User.createTable();
  }
  public static void main(String[] args) {


    // Create a new user
    User newUser = new User("John Doe", "john@example.com");
    newUser.save();
    System.out.println("User created with ID: " + newUser.getId());

    // Find a user by ID
    User foundUser = User.find(User.class, newUser.getId());
    if (foundUser != null) {
      System.out.println("Found user: " + foundUser.getName()
          + ", " + foundUser.getEmail());
    }

    // Update the user
    if (foundUser != null) {
      foundUser.setName("John Smith");
      foundUser.save();
      System.out.println("User updated to: " + foundUser.getName());
    }

    // Delete the user
    if (foundUser != null) {
      foundUser.delete();
      System.out.println("User deleted.");
    }
  }
}
