package com.iluwatar.tabledatagateway;

import java.util.Set;

public interface PersonGatewayInterface {

    Person find(int id);

    Set<Person> findByFirstName(String FName);

    boolean update(int id, String firstName, String lastName, String gender, int age);

    boolean insert(int id, String firstName, String lastName, String gender,int age);

    boolean delete(int id);
}
