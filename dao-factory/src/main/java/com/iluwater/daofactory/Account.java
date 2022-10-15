package com.iluwater.daofactory;

import java.io.Serializable;

/**
 * Account Transfer Object (Transfer Object for Account class)
 */
public class Account implements Serializable {
    // Variable for Account Object
    private int id = -1;
    private String firstName;
    private String lastName;
    private String location;

    public Account() {}

    /**
     * Getter for variable in Account Object
     */
    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLocation() {
        return location;
    }

    /**
     * Setter for variable in Account Object
     */
    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
