package com.iluwatar.remote.facade;

public class Customer {
    private String name;
    private String phone;
    private String address;

    public Customer(String nm, String phn, String adrs) {
        name = nm;
        phone = phn;
        address = adrs;
    }

    //getters and setters

    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public void setName(String name) { this.name = name; }
    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone) { this.phone = phone; }

}
