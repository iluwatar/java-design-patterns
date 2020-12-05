/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.transactionscript;

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
