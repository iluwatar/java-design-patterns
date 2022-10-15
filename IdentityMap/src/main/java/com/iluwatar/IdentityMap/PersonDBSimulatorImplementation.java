package com.iluwatar.IdentityMap;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PersonDBSimulatorImplementation implements PersonDBSimulator {

//    This simulates a database.
    private List<Person> personList = new ArrayList<>();

    @Override
    public Person find(int personNationalID) throws IdNotFoundException {
        for(Person elem : personList)
        {
            if (elem.getPersonNationalId()==personNationalID) {return elem;}
        }
        throw new IdNotFoundException("ID : " + personNationalID + " not in DataBase");
    }

    @Override
    public void insert(Person person) {
        for(Person elem : personList)
        {
            if(elem.getPersonNationalId()==person.getPersonNationalId())
            {
                LOGGER.info("Record already exists.");
                return;
            }
        }
        personList.add(person);
    }

    @Override
    public void update(Person person) throws IdNotFoundException{
        for(Person elem : personList)
        {
            if(elem.getPersonNationalId()==person.getPersonNationalId())
            {
                elem.setName(person.getName());
                elem.setPhoneNum(person.getPhoneNum());
                LOGGER.info("Record updated successfully");
                return;
            }
        }
        throw new IdNotFoundException("ID : " + person.getPersonNationalId() + " not in DataBase");
    }

    @Override
    public void delete(int id) throws IdNotFoundException{
        for(Person elem : personList)
        {
            if(elem.getPersonNationalId()== id)
            {
                personList.remove(elem);
                LOGGER.info("Record deleted successfully.");
                return;
            }
        }
        throw new IdNotFoundException("ID : " + id + " not in DataBase");
    }

    public void insertALl(List<Person> personListInput)
    {
        for(Person elem : personListInput)
            this.insert(elem);
    }
}
