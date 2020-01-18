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

package com.iluwatar.serverless.baas.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import java.io.Serializable;
import java.util.Objects;

/**
 * Address class Created by dheeraj.mummarareddy on 3/4/18.
 */
@DynamoDBDocument
public class Address implements Serializable {

  private static final long serialVersionUID = 6760844284799736970L;

  private String addressLineOne;
  private String addressLineTwo;
  private String city;
  private String state;
  private String zipCode;

  @DynamoDBAttribute(attributeName = "addressLineOne")
  public String getAddressLineOne() {
    return addressLineOne;
  }

  public void setAddressLineOne(String addressLineOne) {
    this.addressLineOne = addressLineOne;
  }

  @DynamoDBAttribute(attributeName = "addressLineTwo")
  public String getAddressLineTwo() {
    return addressLineTwo;
  }

  public void setAddressLineTwo(String addressLineTwo) {
    this.addressLineTwo = addressLineTwo;
  }

  @DynamoDBAttribute(attributeName = "city")
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @DynamoDBAttribute(attributeName = "state")
  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  @DynamoDBAttribute(attributeName = "zipCode")
  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    var address = (Address) o;

    if (!Objects.equals(addressLineOne, address.addressLineOne)) {
      return false;
    }

    if (!Objects.equals(addressLineTwo, address.addressLineTwo)) {
      return false;
    }

    if (!Objects.equals(city, address.city)) {
      return false;
    }

    if (!Objects.equals(state, address.state)) {
      return false;
    }

    return Objects.equals(zipCode, address.zipCode);
  }

  @Override
  public int hashCode() {
    var result = addressLineOne != null ? addressLineOne.hashCode() : 0;
    result = 31 * result + (addressLineTwo != null ? addressLineTwo.hashCode() : 0);
    result = 31 * result + (city != null ? city.hashCode() : 0);
    result = 31 * result + (state != null ? state.hashCode() : 0);
    result = 31 * result + (zipCode != null ? zipCode.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Address{"
        + "addressLineOne='" + addressLineOne + '\''
        + ", addressLineTwo='" + addressLineTwo + '\''
        + ", city='" + city + '\''
        + ", state='" + state + '\''
        + ", zipCode='" + zipCode + '\''
        + '}';
  }
}
