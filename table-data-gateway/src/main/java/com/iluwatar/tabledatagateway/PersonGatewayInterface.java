package com.iluwatar.tabledatagateway;

public interface PersonGatewayInterface {

    Person find(int id);

    Person findByFirstName();

    void update(int id, String firstName, String lastName, String age);

    void insert(int id, String firstName, String lastName, String gender,String age);

    void delete(int id);
}
