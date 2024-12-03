package com.iluwatar;

public class Truck extends Vehicle {
  private double loadCapacity;

  public Truck(int year, String make, String model, double loadCapacity, int id) {
    super(year,make, model, id);
    this.loadCapacity=loadCapacity;
  }

  public void SetLoadCapacity(double capacity){
    this.loadCapacity=capacity;
  }
  public double GetLoadCapacity(){
    return loadCapacity;
  }

  @Override public String toString() {
    return "Truck{" + "id=" + getId() + ", make='" + getMake() + '\'' + ", model='" + getModel() + '\'' + ", year=" + getYear() + ", payloadCapacity=" + GetLoadCapacity() + '}'; }
}