package com.iluwatar.serializedentity;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SerializedEntityTest {
    private static final String DB_URL = "jdbc:h2:~/test";

    private static DataSource createDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(DB_URL);
        return dataSource;
    }

    @BeforeEach
    void setUp() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             var statement = connection.createStatement()) {
            statement.execute(Serialization.DELETE_SCHEMA_SQL);
            statement.execute(Serialization.CREATE_SCHEMA_SQL);
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             var statement = connection.createStatement()) {
            statement.execute(Serialization.DELETE_SCHEMA_SQL);
        }
    }

    @Test
    void testInsertValidID() throws SQLException, IOException {
        DataSource dataSource = createDataSource();
        Taxonomy human = new Taxonomy (
                5,
                "Eukarya",
                "Animalia",
                "Chordata",
                "Mammalia",
                "Primates",
                "Hominidae",
                "Homo",
                "H. sapiens");
        Serialization serializedHuman = new Serialization(human, dataSource);

        assertEquals(5, serializedHuman.insert());
    }

    @Test
    void testReadValidID() throws SQLException, IOException, ClassNotFoundException {
        DataSource dataSource = createDataSource();
        Taxonomy human = new Taxonomy (
                5,
                "Eukarya",
                "Animalia",
                "Chordata",
                "Mammalia",
                "Primates",
                "Hominidae",
                "Homo",
                "H. sapiens");
        Serialization serializedHuman = new Serialization(human, dataSource);

        assertEquals(5, serializedHuman.insert());
        assertEquals(5, serializedHuman.read());
    }


    @Test
    void testUpdateValidID() throws SQLException, IOException, ClassNotFoundException {
        DataSource dataSource = createDataSource();
        Taxonomy human = new Taxonomy (
                5,
                "Eukarya",
                "Animalia",
                "Chordata",
                "Mammalia",
                "Primates",
                "Hominidae",
                "Homo",
                "H. sapiens");


        Taxonomy fruitFly = new Taxonomy (
                2,
                "Eukarya",
                "Animalia",
                "Arthropoda",
                "Insecta",
                "Diptera",
                "Drosophilidae",
                "Drosophila",
                "D. melanogaster");

        Serialization serializedHuman = new Serialization(human, dataSource);

        assertEquals(5, serializedHuman.insert());
        assertEquals(5, serializedHuman.read());

        assertEquals(5, serializedHuman.update(fruitFly));
        assertEquals(5, serializedHuman.read());
    }


    @Test
    void testDeleteValidID() throws SQLException, IOException, ClassNotFoundException {
        DataSource dataSource = createDataSource();
        Taxonomy human = new Taxonomy (
                5,
                "Eukarya",
                "Animalia",
                "Chordata",
                "Mammalia",
                "Primates",
                "Hominidae",
                "Homo",
                "H. sapiens");
        Serialization serializedHuman = new Serialization(human, dataSource);

        assertEquals(5, serializedHuman.insert());
        assertEquals(5, serializedHuman.read());
        assertEquals(5, serializedHuman.delete());
    }
}
