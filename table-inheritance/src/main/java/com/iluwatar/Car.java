package com.iluwatar;

public class Car extends Vehicle {
  private int numDoors;

  public Car(int year, String make, String model, int numDoors, int id) {
    super(year, make, model, id);
    this.numDoors = numDoors;
  }

  public void SetNumDoors(int doors){
    this.numDoors=doors;
  }
  public int GetNumDoors(){
    return numDoors;
  }

  @Override public String toString() {
    return "Car{" + "id=" + getId() + ", make='" + getMake() + '\'' + ", model='" + getModel() + '\'' + ", year=" + getYear() + ", numberOfDoors=" + GetNumDoors() + '}';
  }

}
