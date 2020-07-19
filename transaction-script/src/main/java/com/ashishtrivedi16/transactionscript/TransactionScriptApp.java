package com.ashishtrivedi16.transactionscript;

import com.ashishtrivedi16.transactionscript.db.HotelDAOImpl;
import com.ashishtrivedi16.transactionscript.db.RoomSchemaSql;
import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class TransactionScriptApp {

    private static final String H2_DB_URL = "jdbc:h2:~/test";
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionScriptApp.class);
    private static final String ALL_ROOMS = "customerDao.getAllRooms(): ";

    public static void main(String[] args) throws Exception {

        /*
            TODO
                -> Create in memory database and some sample tables for demo
        */
        final var dataSource = createDataSource();
        createSchema(dataSource);
        final var DAO = new HotelDAOImpl(dataSource);
        try (var customerStream = DAO.getAll()) {
            customerStream.forEach((customer) -> LOGGER.info(customer.toString()));
        }

        deleteSchema(dataSource);

    }

    private static void deleteSchema(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(RoomSchemaSql.DELETE_SCHEMA_SQL);
        }
    }

    private static void createSchema(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
             var statement = connection.createStatement()) {
            statement.execute(RoomSchemaSql.CREATE_SCHEMA_SQL);
        }
    }

    public static DataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl(H2_DB_URL);
        return dataSource;
    }

    private static void addRooms(HotelDAOImpl hotelDAO) throws Exception {
        for (var room : generateSampleRooms()) {
            hotelDAO.add(room);
        }
    }

    public static List<Room> generateSampleRooms() {
        final var room1 = new Room(1, "Single", 50, false);
        final var room2 = new Room(2, "Double", 80, false);
        final var room3 = new Room(3, "Queen", 120, false);
        final var room4 = new Room(4, "King", 150, false);
        final var room5 = new Room(5, "Single", 50, false);
        final var room6 = new Room(6, "Double", 80, false);
        return List.of(room1, room2, room3, room4, room5, room6);
    }

    private static void generate(final HotelDAOImpl hotelDAO) throws Exception {
//        addRooms(hotelDAO);
//        LOGGER.info(ALL_ROOMS);
//        try (var customerStream = hotelDAO.getAll()) {
//            customerStream.forEach((customer) -> LOGGER.info(customer.toString()));
//        }
//        LOGGER.info("hotelDAO.getCustomerById(2): " + hotelDAO.getById(2));
//        final var customer = new Room(4, "Dan", "Danson");
//        hotelDAO.add(customer);
//        LOGGER.info(ALL_ROOMS + hotelDAO.getAll());
//        customer.setFirstName("Daniel");
//        customer.setLastName("Danielson");
//        hotelDAO.update(customer);
//        LOGGER.info(ALL_ROOMS);
//        try (var customerStream = hotelDAO.getAll()) {
//            customerStream.forEach((cust) -> LOGGER.info(cust.toString()));
//        }
//        hotelDAO.delete(customer);
//        LOGGER.info(ALL_ROOMS + hotelDAO.getAll());
    }
}
