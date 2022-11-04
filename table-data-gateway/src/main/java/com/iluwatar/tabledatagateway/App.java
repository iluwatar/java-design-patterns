package com.iluwatar.tabledatagateway;

import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j
public class App {
    public static void main(String[] args) throws SQLException {
        PersonGateway pg = new PersonGateway();
        insertPersons(pg);
        Person WDP = pg.find(01);
        LOGGER.info("Searching 01 in the database give a person whose id is ", WDP.getId());
        pg.insert(07,"YANYAN","WHOYOU","FEMALE", 22);
        LOGGER.info("The new inserted person should have id: ", pg.find(07).getId());
        LOGGER.info("Searching first name HAIJIAN comes the result set is empty: ", pg.findByFirstName("HAIJIAN").isEmpty());
        LOGGER.info("The delete of person whose id is 5 would be ",pg.delete(5));
        LOGGER.info("The update of person whose id is 4 is ",
                    pg.update(04,"YOUYOU","CAI","MALE",22));
        pg.deleteTable();

    }

    private static void insertPersons(PersonGateway pg) {
        pg.database.insert(new Person(01,"DONGPING","WU","MALE",25));
        pg.database.insert(new Person(02,"HAIJIAN","WANG","MALE",25));
        pg.database.insert(new Person(03,"YUNFEI","CHEN","FEMALE",18));
        pg.database.insert(new Person(04,"CHUNHONG","LI","FEMALE",19));
        pg.database.insert(new Person(05,"JIAYU","LI","FEMALE",24));
        pg.database.insert(new Person(06,"ZHENXIAN","GAN","MALE",26));
    }
}
