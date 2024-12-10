package com.iluwatar.visitor.example;
import java.util.HashMap;
import java.util.Map;

public class DataTransferHashMap {
  private Map<String, String> dataMap;

  public DataTransferHashMap() {
    // Initialize the map
    dataMap = new HashMap<>();
  }

  // Method to add data
  public void addData(String key, String value) {
    dataMap.put(key, value);
  }

  // Method to retrieve data
  public String getData(String key) {
    return dataMap.get(key);
  }

  // Method to remove data
  public void removeData(String key) {
    dataMap.remove(key);
  }

  // Method to display all data in the map
  public void displayData() {
    for (Map.Entry<String, String> entry : dataMap.entrySet()) {
      System.out.println(entry.getKey() + ": " + entry.getValue());
    }
  }

  public static void main(String[] args) {
    // Creating an instance of the DataTransferHashMap class
    DataTransferHashMap transfer = new DataTransferHashMap();

    // Adding some data
    transfer.addData("Name", "John");
    transfer.addData("Age", "25");
    transfer.addData("Location", "New York");

    // Displaying all data
    transfer.displayData();

    // Retrieve a single value
    System.out.println("Retrieved Name: " + transfer.getData("Name"));
  }
}

