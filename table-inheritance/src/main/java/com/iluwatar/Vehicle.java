package com.iluwatar;

public class Vehicle{

  private String make, model;
  private int year, id;


  Vehicle(int year, String make, String model, int id){
    this.make=make;
    this.model=model;
    this.year=year;
    this.id=id;
  }

  public void setMake(String make){
    this.make=make;
  }
  public String getMake() {
    return make;
  }

  public void setModel(String model){
    this.model=model;
  }
  public String getModel(){
    return model;
  }

  public void setYear(int year){
    this.year=year;
  }
  public int getYear(){
    return year;
  }

  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }


  @Override public String toString() {
    return "Vehicle{" + "id=" + id + ", make='" + make + '\'' + ", model='" + model + '\'' + ", year=" + year + '}';
  }
}