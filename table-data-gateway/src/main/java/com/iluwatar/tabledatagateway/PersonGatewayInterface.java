package com.iluwatar.tabledatagateway;

import java.util.Set;

public interface PersonGatewayInterface {

    /**
     * find the person with particular id from array
     * @author Taowen Huang
     *
     * @return the person who meet the requirement
     */
    Person find(int id);

    /**
     * find the set of person with particular first name from array
     * @author Taowen Huang
     *
     * @return the set of person with certain first name
     */
    Set<Person> findByFirstName(String FName);

    /**
     * update the information with certain id in database and array
     * @author Taowen Huang
     *
     * @return true if update is successfully implemented, fail otherwise
     */
    boolean update(int id, String firstName, String lastName, String gender, int age);

    /**
     * insert a new person into both database and array
     * @author Taowen Huang
     *
     * @return true if insertion is successfully implemented, fail otherwise
     */
    boolean insert(int id, String firstName, String lastName, String gender,int age);

    /**
     * delete the person with certain id from database and array
     * @author Taowen Huang
     *
     * @return true if deletion is successfully implemented, fail otherwise
     */
    boolean delete(int id);
}
