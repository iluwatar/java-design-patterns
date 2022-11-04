package com.iluwatar.tabledatagateway;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDBTest {

    public PersonDB createTestPDB() throws SQLException {
        PersonDB pdb = new PersonDB();
        Person tP1 = new Person(01,"DONGPING","WU","MALE",25);
        Person tP2 = new Person(02,"HAIJIAN","WANG","MALE",25);
        Person tP3 = new Person(03,"YUNFEI","CHEN","FEMALE",18);
        Person tP4 = new Person(04,"CHUNHONG","LI","FEMALE",19);
        Person tP5 = new Person(05,"JIAYU","LI","FEMALE",24);
        Person tP6 = new Person(06,"ZHENXIAN","GAN","MALE",26);
        pdb.insert(tP1);
        pdb.insert(tP2);
        pdb.insert(tP3);
        pdb.insert(tP4);
        pdb.insert(tP5);
        pdb.insert(tP6);
        return pdb;
    }

    @Test
    void testGetAll() throws SQLException {
        PersonDB pdb = new PersonDB();
        Person tP1 = new Person(01,"DONGPING","WU","MALE",25);
        Person tP3 = new Person(03,"YUNFEI","CHEN","FEMALE",18);
        Person tP5 = new Person(05,"JIAYU","LI","FEMALE",24);
        pdb.insert(tP1);
        pdb.insert(tP3);
        pdb.insert(tP5);
        pdb.personDB.clear();
        assertTrue(pdb.personDB.isEmpty());
        pdb.getAll();
        assertTrue(pdb.personDB.contains(tP1));
        assertTrue(pdb.personDB.contains(tP3));
        assertTrue(pdb.personDB.contains(tP5));
        pdb.deleteTable();
    }

    @Test
    void testSelectByID() throws SQLException{
        PersonDB pdb = createTestPDB();
        assertEquals("DONGPING",pdb.selectByID(01).getFirstName());
        assertEquals("WANG",pdb.selectByID(02).getLastName());
        assertEquals("FEMALE", pdb.selectByID(03).getGender());
        assertEquals(19, pdb.selectByID(04).getAge());
    }

    @Test
    void testSelectByFName() throws SQLException{
        PersonDB pdb = createTestPDB();
        assertTrue(pdb.selectByFirstName("DONGPINGG").isEmpty());
        assertTrue(pdb.selectByFirstName("HAIJIAM").isEmpty());
        assertFalse(pdb.selectByFirstName("YUNFEI").isEmpty());
        assertFalse(pdb.selectByFirstName("JIAYU").isEmpty());
    }

    @Test
    void testDelete() throws SQLException{
        PersonDB pdb = createTestPDB();
        assertTrue(pdb.delete(01));
        assertFalse(pdb.delete(01));
        assertTrue(pdb.delete(02));
        Person tpp = new Person(02,"ALEX","GREEN","MALE",20);
        pdb.insert(tpp);
        assertTrue(pdb.delete(02));
    }

    @Test
    void testUpdate() throws SQLException{
        PersonDB pdb = createTestPDB();
        Person tpp = new Person(02,"ALEX","GREEN","MALE",20);
        Person tpp2 = new Person(02,"SCOTT","WHITE","MALE",20);
        assertTrue(pdb.update(02, tpp));
        assertEquals("ALEX", pdb.selectByID(02).getFirstName());
        assertFalse(pdb.update(10,tpp2));
    }

    @Test
    void testInsert() throws SQLException{
        PersonDB pdb = createTestPDB();
        Person tpp = new Person(8,"ALEX","GREEN","MALE",20);
        assertTrue(pdb.insert(tpp));
        assertEquals("GREEN",pdb.selectByID(8).getLastName());
        Person tpp2 = new Person(9,"SCOTT","WHITE","MALE",22);
        assertTrue(pdb.insert(tpp2));
        assertEquals(22,pdb.selectByID(9).getAge());
    }

    @Test
    void testDeleteTable() throws SQLException {
        PersonDB pdb = createTestPDB();
        pdb.deleteTable();
        pdb.getAll();
        assertTrue(pdb.personDB.isEmpty());
    }
}
