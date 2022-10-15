package com.iluwatar.IdentityMap;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Person definition.
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@AllArgsConstructor
public final class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @EqualsAndHashCode.Include
    private int personNationalId;
    private String name;
    private long phoneNum;

    @Override
    public String toString() {

        return "Person ID is : " + personNationalId + " ; Person Name is : " + name + " ; Phone Number is :"+ phoneNum;

    }

}
