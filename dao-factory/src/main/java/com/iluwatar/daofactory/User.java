package com.iluwatar.daofactory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements java.io.Serializable {
    private int UserId;
    private String name;
    private String streetAddress;
    private String city;

}
