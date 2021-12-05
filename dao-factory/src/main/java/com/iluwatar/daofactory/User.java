package com.iluwatar.daofactory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements java.io.Serializable {
    /**
     * UserId for this User
     */
    private int userId;

    /**
     * Name for this User
     */
    private String name;

    /**
     * Address for this User
     */
    private String streetAddress;

    /**
     * city for this User
     */
    private String city;

}
