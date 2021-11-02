package com.iluwatar.choreography.servicepackage;

public class Package {
    private final long id;
    private final String address;

    public Package(long id, String address) {
        this.id = id;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public long getId() {
        return id;
    }
}
