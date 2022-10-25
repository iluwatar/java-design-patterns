package com.iluwatar.tabledatagateway;
import java.sql.*;

public class PersonGateway implements PersonGatewayInterface{

    public Person find(int id) {
        return new Person(0,null,null,null,0);
    }

    public Person findByFirstName() {
        return new Person(0,null,null,null,0);
    }

    public void update(int id, String firstName, String lastName, String age) {

    }

    public void insert(int id, String firstName, String lastName, String gender, String age) {

    }

    public void delete(int id) {

    }
}
