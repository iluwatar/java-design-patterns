package com.iluwatar.tabledatagateway;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.sql.*;
import org.h2.jdbcx.JdbcDataSource;

public class PersonDB implements Database{
    ArrayList<Person> personDB;
    private final JdbcDataSource dataSource = new JdbcDataSource();

    private static final String DBURL = "jdbc:h2:~/tabledatagateway";

    public PersonDB() throws SQLException {
        dataSource.setURL(DBURL);
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("CREATE TABLE PERSONS (ID NUMBER, FIRSTNAME VARCHAR(100), "
                + "LASTNAME VARCHAR(100), GENDER VARCHAR(100), AGE NUMBER)");
    }

    public void getAll() {
        try{
            Connection con = dataSource.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM PERSONS");
            while (!rs.next()){
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
            if (!rs.next()){
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
            while (!rs.next()){
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
            return st.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
