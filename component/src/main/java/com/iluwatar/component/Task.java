package com.iluwatar.component;

public interface Task {
  String getTitle();
  void setTitle(String title);
  void addTask(Task task);
  void removeTask(Task task);
  void display();
}

