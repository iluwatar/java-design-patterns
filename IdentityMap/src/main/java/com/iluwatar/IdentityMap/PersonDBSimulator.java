package com.iluwatar.IdentityMap;

import java.util.Optional;

public interface PersonDBSimulator {
    Person find(int personNationalID);

    void insert(Person person);
    void update(Person person);
    void delete(int personNationalID);

}
