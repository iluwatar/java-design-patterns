package com.iluwatar.tabledatagateway;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class PersonGatewayTest {

    private PersonGateway createTestPG() throws SQLException {
        PersonGateway pg = new PersonGateway();
        pg.database.insert(new Person(01,"DONGPING","WU","MALE",25));
        pg.database.insert(new Person(02,"HAIJIAN","WANG","MALE",25));
        pg.database.insert(new Person(03,"YUNFEI","CHEN","FEMALE",18));
        pg.database.insert(new Person(04,"CHUNHONG","LI","FEMALE",19));
        pg.database.insert(new Person(05,"JIAYU","LI","FEMALE",24));
        pg.database.insert(new Person(06,"ZHENXIAN","GAN","MALE",26));
        return pg;
    }

    @Test
    void testfind() throws SQLException{
        PersonGateway pg = createTestPG();
        assertEquals("DONGPING",pg.find(01).getFirstName());
        assertEquals("WANG",pg.find(02).getLastName());
        assertEquals("FEMALE", pg.find(03).getGender());
        assertEquals(19, pg.find(04).getAge());
    }

    @Test
    void testFindByFName() throws SQLException{
        PersonGateway pg = createTestPG();
        assertTrue(pg.findByFirstName("DONGPINGG").isEmpty());
        assertTrue(pg.findByFirstName("HAIJIAM").isEmpty());
        assertFalse(pg.findByFirstName("YUNFEI").isEmpty());
        assertFalse(pg.findByFirstName("JIAYU").isEmpty());
    }

    @Test
    void testDelete() throws  SQLException{
        PersonGateway pg = createTestPG();
        assertTrue(pg.delete(01));
        assertFalse(pg.delete(01));
        assertTrue(pg.delete(02));
        pg.insert(02,"ALEX","GREEN","MALE",20);
        assertTrue(pg.delete(02));
    }

    @Test
    void testUpdate() throws SQLException{
        PersonGateway pg = createTestPG();
        assertTrue(pg.update(02,"ALEX","GREEN","MALE",20));
        assertEquals("ALEX", pg.find(02).getFirstName());
        assertFalse(pg.update(10,"SCOTT","WHITE","MALE",20));
    }

    @Test
    void testInsert() throws SQLException{
        PersonGateway pg = createTestPG();
        assertTrue(pg.insert(8,"ALEX","GREEN","MALE",20));
        assertEquals("GREEN",pg.find(8).getLastName());
        assertTrue(pg.insert(9,"SCOTT","WHITE","MALE",22));
        assertEquals(22,pg.find(9).getAge());
    }
}
