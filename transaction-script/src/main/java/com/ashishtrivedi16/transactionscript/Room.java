package com.ashishtrivedi16.transactionscript;

/**
 * A room POJO that represents the data that will be read from the data source.
 */
public class Room {

  private int id;
  private String roomType;
  private int price;
  private boolean booked;

  /**
   * Create an instance of room.
   * @param id room id
   * @param roomType room type
   * @param price room price
   * @param booked room booking status
   */
  public Room(int id, String roomType, int price, boolean booked) {
    this.id = id;
    this.roomType = roomType;
    this.price = price;
    this.booked = booked;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getRoomType() {
    return roomType;
  }

  public void setRoomType(String roomType) {
    this.roomType = roomType;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public boolean isBooked() {
    return booked;
  }

  public void setBooked(boolean booked) {
    this.booked = booked;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Room room = (Room) o;

    if (id != room.id) {
      return false;
    }
    if (price != room.price) {
      return false;
    }
    if (booked != room.booked) {
      return false;
    }
    return roomType.equals(room.roomType);
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + roomType.hashCode();
    result = 31 * result + price;
    result = 31 * result + (booked ? 1 : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Room{"
        + "id=" + id
        + ", roomType=" + roomType
        + ", price=" + price
        + ", booked=" + booked
        + '}';
  }
}
