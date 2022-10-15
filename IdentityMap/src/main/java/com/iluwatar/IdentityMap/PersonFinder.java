package com.iluwatar.IdentityMap;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonFinder {
    private IdentityMap identityMap = new IdentityMap();
    private PersonDBSimulatorImplementation DB;

    public Person getPerson(int key)
    {
        Person person = this.identityMap.getPerson(key);
        if(person != null)
        {
            LOGGER.info("Person found in the Map");
            return person;
        }
        else
        {
            person = this.DB.find(key);
            if(person!=null)
            {
                this.identityMap.addPerson(person);
                LOGGER.info("Person found in DB.");
                return person;
            }
            LOGGER.info("Person with this ID does not exist.");
            return null;
        }
    }

    public PersonDBSimulatorImplementation getDB() {
        return DB;
    }

    public IdentityMap getIdentityMap() {
        return identityMap;
    }

    public void setDB(PersonDBSimulatorImplementation DB) {
        this.DB = DB;
    }

    public void setIdentityMap(IdentityMap identityMap) {
        this.identityMap = identityMap;
    }
}
