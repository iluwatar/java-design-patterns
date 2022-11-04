package com.iluwatar.tabledatagateway;

import java.sql.*;

public class PersonGateway implements PersonGatewayInterface{

    public PersonDB database;

    public PersonGateway() throws SQLException {
        database = new PersonDB();
    }

    public Person find(int id) {
        for (Person ps: database.personDB){
            if (ps.getId() == id){
                return ps;
            }
        }
        return null;
    }

    public Person findByFirstName(String FName) {
        for (Person ps: database.personDB){
            if (ps.getFirstName() == FName){
                return ps;
            }
        }
        return null;
    }

    public boolean update(int id, String firstName, String lastName, String gender, int age) {
        if (database.update(id, new Person(id,firstName,lastName,gender,age))){
            for (Person ps: database.personDB){
                if (ps.getId() == id){
                    ps.setAge(age);
                    ps.setLastName(lastName);
                    ps.setFirstName(firstName);
                    ps.setGender(gender);
                }
            }
            return true;
        }
        return false;

    }

    public boolean insert(int id, String firstName, String lastName, String gender, int age) {
        database.insert(new Person(id,firstName,lastName,gender,age));
        for (Person ps: database.personDB){
            if (ps.getId() == id){
                ps.setAge(age);
                ps.setLastName(lastName);
                ps.setFirstName(firstName);
                ps.setGender(gender);
            }
        }
        return true;
    }

    public boolean delete(int id) {
        if (database.delete(id)){
            Person removedPS = null;
            for (Person ps: database.personDB){
                if (ps.getId() == id){
                    removedPS = ps;
                }
            }
            database.personDB.remove(removedPS);
            return true;
        }
        return false;
    }
}
