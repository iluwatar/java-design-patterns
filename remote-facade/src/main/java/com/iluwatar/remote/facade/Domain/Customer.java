package com.iluwatar.remote.facade.Domain;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Customer {
    private String name;
    private String phone;
    private String address;

    public Customer(String nm, String phn, String adrs) {
        name = nm;
        phone = phn;
        address = adrs;
    }

}
