package com.iluwatar.daofactory;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;


/**
 * DerbyUserDAO implementation of the
 * UserDAO interface. This class can contain all
 * Derby specific code and SQL statements.
 * The client is thus shielded from knowing
 * these implementation details.
 *
 */
@Slf4j
public class DerbyUserDAO implements UserDAO{
    Connection con = DerbyDAOFactory.createConnection();

    /**
     * Creates a table DERBYUSER in DerbyDB.
     */
    public DerbyUserDAO() {
        final String SQL_CREATE = "CREATE TABLE DERBYUSER"
                + "("
                + " ID INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY(Start with 1, Increment by 1),"
                + " NAME VARCHAR(140) NOT NULL,"
                + " ADDRESS VARCHAR(140) NOT NULL,"
                + " CITY VARCHAR(140) NOT NULL"
                + ")";

        try (Statement stmt = con.createStatement();) {
            final DatabaseMetaData dbm = con.getMetaData();
            final ResultSet rs = dbm.getTables(null, "APP", "DERBYUSER", null);
            if (!rs.next()) {
                stmt.execute(SQL_CREATE);
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("Table created");
                }
            }else{
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("Table already exists");
                }
            }

        } catch (SQLException e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    /**
     * Insert user to DerbyUser.
     *
     * @param user
     * @return newly created user number or -1 on error
     */
    @Override
    public int insertUser(final User user) {
        int last_inserted_id = -1;
        try (PreparedStatement statement = con.prepareStatement("INSERT INTO DERBYUSER(NAME, ADDRESS, CITY) " +
            "VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getStreetAddress());
            statement.setString(3, user.getCity());
            statement.execute();
            final ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                last_inserted_id = rs.getInt(1);
            }

        } catch (SQLException e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(e.getMessage());
            }
        }
        return last_inserted_id;
    }

    /**
     * Delete user in DerbyUser.
     *
     * @param user
     * @return true on success, false on failure
     */
    @Override
    public boolean deleteUser(final User user) {

        final int id = user.getUserId();
        try (PreparedStatement stmt =con.prepareStatement("DELETE FROM DERBYUSER WHERE ID = ?");) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(e.getMessage());
            }
        }

        return false;
    }

    /**
     * Find a user in DerbyUser using userId.
     *
     * @param userId
     * @return a User Object if found, return null on error or if not found
     */
    @Override
    public User findUser(final int userId) {

        final User user = new User();
        int id = -1;
        String address = "";
        String name = "";
        String city = "";
        try (Statement sta = con.createStatement();
             ResultSet res = sta.executeQuery(
            "SELECT * FROM DERBYUSER WHERE ID = " + userId);) {

            while (res.next()) {
                id = res.getInt("ID");
                address = res.getString("ADDRESS");
                name = res.getString("NAME");
                city = res.getString("CITY");
            }
            res.close();
            sta.close();
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(e.getMessage());
            }
        }
        user.setUserId(id);
        user.setName(name);
        user.setStreetAddress(address);
        user.setCity(city);
        return user;
    }

    /**
     * Update record here using data from the User Object
     *
     * @param user
     * @return true on success, false on failure or error
     */
    @Override
    public boolean updateUser(final User user) {
        try (Statement stmt = con.createStatement();
             PreparedStatement preparedStatement =
                 con.prepareStatement("UPDATE DERBYUSER SET NAME = ? , " +
                 "ADDRESS = ?, CITY = ? WHERE ID = ?");) {
            final int id = user.getUserId();
            final String newName = user.getName();
            final String newAddress = user.getStreetAddress();
            final String newCity = user.getCity();

            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, newAddress);
            preparedStatement.setString(3, newCity);
            preparedStatement.setInt(4, id);
            return preparedStatement.executeUpdate() > 0;

        }catch (SQLException throwables) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(throwables.getMessage());
            }
        }

        return false;
    }

    /**
     * Search users here using the supplied criteria.
     *
     * @param criteriaCol, criteria
     * @return Collection of users found using the criteria
     */
    @Override
    public Collection selectUsersTO(final String criteriaCol, final String criteria) {
        final ArrayList<User> selectedUsers = new ArrayList<>();

        try (Statement sta = con.createStatement();
             ResultSet res = sta.executeQuery("SELECT ID, Address, Name, City " +
            "FROM DERBYUSER WHERE "+criteriaCol+" = '" + criteria + "'");) {

            while (res.next()) {
                final User user = new User();
                user.setUserId(res.getInt("ID"));
                user.setStreetAddress(res.getString("ADDRESS"));
                user.setName(res.getString("NAME"));
                user.setCity(res.getString("CITY"));
                selectedUsers.add(user);

            }
            res.close();
            sta.close();
        } catch (Exception e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(e.getMessage());
            }
        }

        return selectedUsers;
    }

}
