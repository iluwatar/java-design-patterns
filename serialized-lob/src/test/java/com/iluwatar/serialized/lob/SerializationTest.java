package com.iluwatar.serialized.lob;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SerializationTest {
    private static final String DB_URL = "jdbc:h2:~/test";

    private static DataSource createDataSource() {
        var dataSource = new JdbcDataSource();
        dataSource.setURL(DB_URL);
        return dataSource;
    }

    @SuppressFBWarnings("RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE")
    @BeforeEach
    void setUp() throws SQLException {
        try (var connection = DriverManager.getConnection(DB_URL);
             var statement = connection.createStatement()) {
            statement.execute(Serialization.DELETE_SCHEMA_SQL);
            statement.execute(Serialization.CREATE_SCHEMA_SQL);
        }
    }

    @SuppressFBWarnings("RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE")
    @AfterEach
    void tearDown() throws SQLException {
        try (var connection = DriverManager.getConnection(DB_URL);
             var statement = connection.createStatement()) {
            statement.execute(Serialization.DELETE_SCHEMA_SQL);
        }
    }

    @Test
    void testGetPosition() {
        var devDept = new Department("Developing", null);
        var developer = new Position(3, "Developer", List.of(devDept));

        assertEquals(3, developer.getId());
        assertEquals("Developer", developer.getName());
        assertNotNull(developer.getDepartments());
    }

    @Test
    void testGetPositionIds() {
        var devDept = new Department("Developing", null);
        var testDept = new Department("Testing", null);
        var devManageDept = new Department("Development Management", List.of(devDept));
        var testManageDept = new Department("Testing Management", List.of(testDept));
        var engineeringManageDept = new Department("Engineering Management", List.of(devManageDept, testManageDept));

        var CTO = new Position(1, "CTO", List.of(engineeringManageDept));
        var director = new Position(2, "Director", List.of(devManageDept, testManageDept));
        var developer = new Position(3, "Developer", List.of(devDept));
        var tester = new Position(5, "Tester", List.of(testDept));

        assertEquals(1, CTO.getId());
        assertEquals(2, director.getId());
        assertEquals(3, developer.getId());
        assertEquals(5, tester.getId());
    }

    @Test
    void testGetPositionNames() {
        var devDept = new Department("Developing", null);
        var testDept = new Department("Testing", null);
        var devManageDept = new Department("Development Management", List.of(devDept));
        var testManageDept = new Department("Testing Management", List.of(testDept));
        var engineeringManageDept = new Department("Engineering Management", List.of(devManageDept, testManageDept));

        var CTO = new Position(1, "CTO", List.of(engineeringManageDept));
        var director = new Position(2, "Director", List.of(devManageDept, testManageDept));
        var developer = new Position(3, "Developer", List.of(devDept));
        var tester = new Position(5, "Tester", List.of(testDept));

        assertNotNull(CTO.getName());
        assertNotNull(director.getName());
        assertNotNull(developer.getName());
        assertNotNull(tester.getName());

        assertEquals("CTO", CTO.getName());
        assertEquals("Director", director.getName());
        assertEquals("Developer", developer.getName());
        assertEquals("Tester", tester.getName());
    }

    @Test
    void testGetPositionDepartments() {
        var devDept = new Department("Developing", null);
        var testDept = new Department("Testing", null);
        var devManageDept = new Department("Development Management", List.of(devDept));
        var testManageDept = new Department("Testing Management", List.of(testDept));
        var engineeringManageDept = new Department("Engineering Management", List.of(devManageDept, testManageDept));

        var CTO = new Position(1, "CTO", List.of(engineeringManageDept));
        var director = new Position(2, "Director", List.of(devManageDept, testManageDept));
        var developer = new Position(3, "Developer", List.of(devDept));
        var tester = new Position(5, "Tester", List.of(testDept));

        assertNotNull(CTO.getDepartments());
        assertNotNull(director.getDepartments());
        assertNotNull(developer.getDepartments());
        assertNotNull(tester.getDepartments());

        assertEquals(1, CTO.getDepartments().size());
        assertEquals(2, director.getDepartments().size());
        assertEquals(1, developer.getDepartments().size());
        assertEquals(1, tester.getDepartments().size());
    }

    @Test
    void testGetDepartmentNames() {
        var devDept = new Department("Developing", null);
        var testDept = new Department("Testing", null);
        var devManageDept = new Department("Development Management", List.of(devDept));
        var testManageDept = new Department("Testing Management", List.of(testDept));
        var engineeringManageDept = new Department("Engineering Management", List.of(devManageDept, testManageDept));

        assertNotNull(devDept.getName());
        assertNotNull(testDept.getName());
        assertNotNull(devManageDept.getName());
        assertNotNull(testManageDept.getName());
        assertNotNull(engineeringManageDept.getName());

        assertEquals("Developing", devDept.getName());
        assertEquals("Testing", testDept.getName());
        assertEquals("Development Management", devManageDept.getName());
        assertEquals("Testing Management", testManageDept.getName());
        assertEquals("Engineering Management", engineeringManageDept.getName());
    }

    @Test
    void testGetDepartmentSubsidiaries() {
        var devDept = new Department("Developing", null);
        var testDept = new Department("Testing", null);
        var devManageDept = new Department("Development Management", List.of(devDept));
        var testManageDept = new Department("Testing Management", List.of(testDept));
        var engineeringManageDept = new Department("Engineering Management", List.of(devManageDept, testManageDept));

        assertNull(devDept.getSubsidiaries());
        assertNull(testDept.getSubsidiaries());
        assertNotNull(devManageDept.getSubsidiaries());
        assertNotNull(testManageDept.getSubsidiaries());
        assertNotNull(engineeringManageDept.getSubsidiaries());

        assertEquals(1, devManageDept.getSubsidiaries().size());
        assertEquals(1, testManageDept.getSubsidiaries().size());
        assertEquals(2, engineeringManageDept.getSubsidiaries().size());
    }

    @Test
    void testInsert_GivenValidID_ShallSucceed() throws SQLException, IOException {
        var dataSource = createDataSource();
        var devDept = new Department("Developing", null);
        var developer = new Position(3, "Developer", List.of(devDept));
        var serializationDeveloper = new Serialization(developer, dataSource);

        assertEquals(3, serializationDeveloper.insert());
    }

    @Test
    void testRead_GivenValidID_ShallSucceed() throws SQLException, IOException, ClassNotFoundException {
        var dataSource = createDataSource();
        var devDept = new Department("Developing", null);
        var developer = new Position(3, "Developer", List.of(devDept));
        var serializationDeveloper = new Serialization(developer, dataSource);

        assertEquals(3, serializationDeveloper.insert());
        assertEquals(3, serializationDeveloper.read());
    }

    @Test
    void testUpdate_GivenValidID_ShallSucceed() throws SQLException, IOException {
        var dataSource = createDataSource();
        var devDept = new Department("Developing", null);
        var developer = new Position(3, "Developer", List.of(devDept));
        var testDept = new Department("Testing", null);
        var tester = new Position(3, "Tester", List.of(testDept));
        var serializationDeveloper = new Serialization(developer, dataSource);

        assertEquals(3, serializationDeveloper.update(tester));
    }

    @Test
    void testDelete_GivenValidID_ShallSuccess() throws SQLException {
        var dataSource = createDataSource();
        var devDept = new Department("Developing", null);
        var developer = new Position(3, "Developer", List.of(devDept));
        var serializationDeveloper = new Serialization(developer, dataSource);

        assertEquals(3, serializationDeveloper.delete());
    }
}
