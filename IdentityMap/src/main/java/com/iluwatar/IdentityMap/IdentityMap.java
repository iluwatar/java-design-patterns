package com.iluwatar.IdentityMap;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class IdentityMap {
    private HashMap<Integer,Person> personMap = new HashMap<>();

    public void addPerson(Person person)
    {
        personMap.put(person.getPersonNationalId(),person);
    }

    public Person getPerson(int id)
    {
        Person person = personMap.get(id);
        if(person ==null)
        {
//            LOGGER.info("ID not in Map.");
        }
        return person;
    }

}
