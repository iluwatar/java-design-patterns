package com.iluwatar.tabledatagateway;

import java.util.ArrayList;
import java.util.Set;

public interface Database {
    void getAll();

    Person selectByID(int ID) ;

    Set<Person> selectByFirstName(String FName);

    boolean delete(int id) ;

    boolean update(int id, Person obj);

    boolean insert(Person obj);
}
