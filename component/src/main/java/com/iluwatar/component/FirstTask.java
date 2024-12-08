package com.iluwatar.component;
public class FirstTask implements Task {
  private String title;

  public FirstTask(String title) {
    this.title = title;
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public void addTask(Task task) {
    // Not applicable for FirstTask, as it's a leaf node.
  }

  @Override
  public void removeTask(Task task) {
    // Not applicable for FirstTask, as it's a leaf node.
  }

  @Override
  public void display() {
    System.out.println("FirstTask: " + title);
  }
}



