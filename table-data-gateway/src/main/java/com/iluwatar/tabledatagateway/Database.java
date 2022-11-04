package com.iluwatar.tabledatagateway;

import java.util.ArrayList;
import java.util.Set;

public interface Database {
    /**
     * Store all the data in the SQL database into the data structure in the program
     * @author Taowen Huang
     *
     */
    void getAll();

    /**
     * select the person by id from the database
     * @author Taowen Huang
     *
     * @return The Person that is found in the data structure
     */
    Person selectByID(int ID) ;

    /**
     * select the person by firstname from the database
     * @author Taowen Huang
     *
     * @return return the set of Person who meet the requirement
     */
    Set<Person> selectByFirstName(String FName);

    /**
     * delete a particular person in the database
     * @author Taowen Huang
     *
     * @return true if the data is successfully deleted, false means no such data in it
     */
    boolean delete(int id) ;

    /**
     * update the data of certain person in the database
     * @author Taowen Huang
     *
     * @return true if the data is successfully updated, false means no such data in it
     */
    boolean update(int id, Person obj);

    /**
     * insert a new person into the database
     * @author Taowen Huang
     *
     * @return true if the data is successfully inserted, false insertion fail
     */
    boolean insert(Person obj);
}
