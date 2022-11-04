package com.iluwatar.tabledatagateway;

public interface PersonGatewayInterface {

    Person find(int id);

    Person findByFirstName(String FName);

    boolean update(int id, String firstName, String lastName, String gender, int age);

    boolean insert(int id, String firstName, String lastName, String gender,int age);

    boolean delete(int id);
}
