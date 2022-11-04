package com.iluwatar.serialized.lob;

import org.h2.jdbcx.JdbcDataSource;
import org.jdom2.JDOMException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that the customer class works correctly
 */
class CustomerTest {
    private static final String DB_URL = "jdbc:h2:~/test";

    private static DataSource createDataSource() {
        var dataSource = new JdbcDataSource();
        dataSource.setURL(DB_URL);
        return dataSource;
    }

    @BeforeEach
    void setUp() throws SQLException {
        // Connect to database
        try (var connection = DriverManager.getConnection(DB_URL);
             var statement = connection.createStatement()) {
            // Create schema
            statement.execute(Customer.DELETE_SCHEMA_SQL);
            statement.execute(Customer.CREATE_SCHEMA_SQL);
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (var connection = DriverManager.getConnection(DB_URL);
             var statement = connection.createStatement()) {
            statement.execute(Customer.DELETE_SCHEMA_SQL);
        }
    }

    @Test
    void testAddDepartment() {
        var customer = new Customer("customer", 1);
        var department1 = new Department("department1");
        var department2 = new Department("department2");
        customer.getDepartments().add(department1);
        customer.getDepartments().add(department2);
        assertEquals(2, customer.getDepartments().size());
    }

    @Test
    void testGetDepartments() {
        var customer = new Customer("customer", 1);
        assertNotNull(customer.getDepartments());
    }

    @Test
    void testStringToElement() throws IOException, JDOMException {
        var str = "<departmentList><department name=\"department1\"><department" +
                " name=\"department2\"><department name=\"department3\"" +
                " /></department></department></departmentList>";
        assertNotNull(Customer.stringToElement(str));
    }

    @Test
    void testElementToString() {
        var customer = new Customer("customer", 1);
        assertNotNull(Customer.elementToString(customer.departmentsToXMLElement()));
    }

    @Test
    void testInsert() throws SQLException {
        var customer = new Customer("customer", 1);
        var dataSource = createDataSource();
        assertNotEquals(0, customer.insert(dataSource));
    }

    @Test
    void testLoad() throws IOException, JDOMException, SQLException {
        var customer = new Customer("customer", 1);
        var dataSource = createDataSource();
        customer.insert(dataSource);
        assertNotNull(customer.load(1, dataSource));
    }

    @Test
    void testDepartmentsToXmlElement() {
        var customer = new Customer("customer", 1);
        assertNotNull(customer.departmentsToXMLElement());
    }

    @Test
    void testReadDepartments() {
        var customer = new Customer("customer", 1);
        var department = new Department("department");
        customer.getDepartments().add(department);
        customer.readDepartments(customer.departmentsToXMLElement());
        assertNotEquals(0, customer.getDepartments().size());
    }
}