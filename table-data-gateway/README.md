---
layout: pattern
title: Table data gateway
folder: Table-data-gateway
permalink: /patterns/Table-data-gateway/
categories: Structural
language: en
tags:
 -
---

## Intent
An object that acts as a gateway to database tables. One instance handles all the rows in the table.


## Explanation

Real-world example

> Imagine that in the game you have a skill pack that includes all your skills, attack/defense/healing, etc. 
> You can implement all skills by calling this skill pack directly.
> without knowing what instructions each specific skill needs to implement. 
> By this way you could use all the commands more conveniently.

In plain words

>An object that acts as a gateway to a database table. One instance handles all the rows in
the table. It encapsulates sql queries as methods.

Wikipedia says

> Table Data Gateway is a design pattern in which an object acts as a gateway to a database table.
> The idea is to separate the responsibility of fetching items from a database from the actual usages of those objects.
> Users of the gateway are then insulated from changes to the way objects are stored in the database.

**Programmatic Example**

We have `Database` interface includes all the behaviors, and `PersonDB` java class to implement it.


```java

public interface Database {
    void getAll();

    Person selectByID(int ID) ;

    Set<Person> selectByFirstName(String FName);

    boolean delete(int id) ;

    boolean update(int id, Person obj);

    boolean insert(Person obj);
}

public class PersonDB implements Database{
    public ArrayList<Person> personDB;
    private final JdbcDataSource dataSource = new JdbcDataSource();

    private static final String DBURL = "jdbc:h2:~/tabledatagateway";

    public PersonDB() throws SQLException {
        dataSource.setURL(DBURL);
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE PERSONS (ID NUMBER, FIRSTNAME VARCHAR(100), "
                + "LASTNAME VARCHAR(100), GENDER VARCHAR(100), AGE NUMBER)");
        personDB = new ArrayList<Person>();
    }

    public void getAll() {
        try{
            Connection con = dataSource.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PERSONS");
            while (rs.next()){
                personDB.add(new Person(rs.getInt("ID"),
                        rs.getString("FIRSTNAME"), rs.getString("LASTNAME"),
                        rs.getString("GENDER"), rs.getInt("AGE")));
            }
            rs.close();
            st.close();
            con.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Person selectByID(int ID) {
        Person ps = new Person();
        try{
            Connection con = dataSource.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PERSONS WHERE ID = " + String.valueOf(ID));
            if (rs.next()){
                ps.setId((rs.getInt("ID")));
                ps.setFirstName(rs.getString("FIRSTNAME"));
                ps.setLastName(rs.getString("LASTNAME"));
                ps.setGender(rs.getString("GENDER"));
                ps.setAge(rs.getInt("AGE"));
            } else {
                rs.close();
                st.close();
                con.close();
                throw new QueryException("NO REQUIRED PERSON WITH SEARCHING ID");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ps;
    }

    public Set<Person> selectByFirstName(String FName) {
        Set<Person> setPerson = new HashSet<Person>();
        try{
            Connection con = dataSource.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PERSONS WHERE ID = " + FName);
            while (rs.next()){
                setPerson.add(new Person(rs.getInt("ID"),
                        rs.getString("FIRSTNAME"), rs.getString("LASTNAME"),
                        rs.getString("GENDER"), rs.getInt("AGE")));
            }
            rs.close();
            st.close();
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return setPerson;
    }

    public boolean delete(int id) {
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement st = con.prepareStatement("DELETE FROM PERSONS WHERE ID = ?");
            st.setInt(1, id);
            Person removedPS = null;
            for (Person ps: personDB){
                if (ps.getId() == id){
                    removedPS = ps;
                }
            }
            personDB.remove(removedPS);
            return st.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    public boolean update(int id, Person ps) {
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement st = con.prepareStatement("UPDATE PERSONS SET FIRSTNAME = ?, LASTNAME = ?, GENDER = ?,AGE = ? WHERE ID = ?");
            st.setString(1, ps.getFirstName());
            st.setString(2, ps.getLastName());
            st.setString(3, ps.getGender());
            st.setInt(4, ps.getAge());
            st.setInt(5, ps.getId());
            for (Person pss: personDB){
                if (pss.getId() == id){
                    pss.setGender(ps.getGender());
                    pss.setLastName(ps.getLastName());
                    pss.setAge(ps.getAge());
                    pss.setFirstName(ps.getFirstName());
                    pss.setId(ps.getId());
                }
            }
            return st.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean insert(Person ps) {
        try {
            Connection con = dataSource.getConnection();
            PreparedStatement st = con.prepareStatement("INSERT INTO PERSONS VALUES (?,?,?,?,?)");
            st.setInt(1, ps.getId());
            st.setString(2, ps.getFirstName());
            st.setString(3, ps.getLastName());
            st.setString(4, ps.getGender());
            st.setInt(5, ps.getAge());
            personDB.add(ps);
            return st.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void deleteTable(){
        try {
            Connection con = dataSource.getConnection();
            Statement st = con.createStatement();
            st.execute("DROP TABLE PERSONS");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}




```

Then, we have interface `PersonGatewayInterface`, and java class `PersonGateway` to implement.

```java

public interface PersonGatewayInterface {

    Person find(int id);

    Set<Person> findByFirstName(String FName);

    boolean update(int id, String firstName, String lastName, String gender, int age);

    boolean insert(int id, String firstName, String lastName, String gender,int age);

    boolean delete(int id);
}

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

    public Set<Person> findByFirstName(String FName) {
        Set<Person> setPerson = new HashSet<Person>();
        for (Person ps: database.personDB){
            if (ps.getFirstName() == FName){
                setPerson.add(ps);
            }
        }
        return setPerson;
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
            return true;
        }
        return false;
    }

    public void deleteTable(){
        database.deleteTable();
    }
}

``` 

We have the `App` to run the logger information result.

```java

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

```



## Applicability
Use the Twin idiom when

* To use the same interface to manipulate the database using SQL, as well as using stored procedures
* To generate the recordset data structure for the table module.

