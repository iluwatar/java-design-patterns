package com.iluwatar.tabledatagateway;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        PersonGateway pg = new PersonGateway();
        insertPersons(pg);
        Person bfwu = pg.find(01);

    }

    private static void insertPersons(PersonGateway pg) {
        pg.database.insert(new Person(01,"DONGPING","WU","MALE",25));
        pg.database.insert(new Person(02,"HAIJIAN","WANG","MALE",25));
        pg.database.insert(new Person(03,"YUNFEI","CHEN","FEMALE",18));
        pg.database.insert(new Person(04,"CHUNHONG","LI","FEMALE",19));
        pg.database.insert(new Person(05,"JIAYU","LI","FEMALE",24));
        pg.database.insert(new Person(01,"ZHENXIAN","GAN","MALE",26));
    }
}
