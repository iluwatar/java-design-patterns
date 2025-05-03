package com.iluwatar.daofactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * FlatFileDataSourceFactory concrete factory.
 */
public class FlatFileDataSourceFactory extends DAOFactory {
  private final String FILE_PATH = System.getProperty("user.home") + "/Desktop/customer.json";
  @Override
  public CustomerDAO createCustomerDAO() {
    Path filePath = Paths.get(FILE_PATH);
    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .serializeNulls()
        .create();
    return new FlatFileCustomerDAO(filePath, gson);
  }
}
