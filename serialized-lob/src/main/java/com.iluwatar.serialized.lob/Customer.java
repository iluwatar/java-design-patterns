package com.iluwatar.serialized.lob;

import java.io.IOException;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;
import javax.xml.XMLConstants;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;


@Slf4j
public class Customer {
    /** Static constants */
    public static final String CREATE_SCHEMA_SQL =
            "CREATE TABLE IF NOT EXISTS CUSTOMERS (ID NUMBER UNIQUE, NAME"
                    + " VARCHAR(30),DEPARTMENTS VARCHAR)";
    public static final String DELETE_SCHEMA_SQL = "DROP TABLE CUSTOMERS IF "
            + "EXISTS";
    public static final String INSERT_TO_CUSTOMERS_SQL = "insert into customers "
            + "(id,name, departments) values (?,?,?)";
    public static final String SELECT_DEPARTMENTS_SQL = "select departments from "
            + "customers where id = ?";
    public static final String DEPARTMENT_COLUMN_LABEL = "departments";
    public static final String XML_ELEMENT_ROOT = "departmentList";
    public static final String XML_ELEMENT_CHILD_NAME = "department";

    /** Customer fields */
    private final long id;
    private final String name;
    private final ArrayList<Department> departments = new ArrayList<>();

    /**
     * Protected constructor
     *
     * @param name name
     * @param id   id
     */
    protected Customer(final String name, final long id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Get method for the departments field
     *
     * @return List of the customer's departments
     */
    protected ArrayList<Department> getDepartments() {
        return departments;
    }

    /**
     * Convert a String to an XML element
     *
     * @param xmlstr String to convert
     * @return XML element
     * @throws IOException   if any error
     * @throws JDOMException if any error
     */
    protected static Element stringToElement(final String xmlstr)
            throws IOException, JDOMException {
        var stringReader = new StringReader(xmlstr);
        var builder = new SAXBuilder();
        builder.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        builder.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        var doc = builder.build(stringReader);
        var elem = doc.getRootElement();
        return elem.detach();
    }

    /**
     * Convert XML element to easily readable String
     *
     * @param element XML element to convert
     * @return String version of XML element
     */
    protected static String elementToString(final Element element) {
        var xmlOutput = new XMLOutputter();
        return xmlOutput.outputString(element);
    }

    /**
     * Load a serialized XML element of the customer's departments from the database
     *
     * @param id        id of the desired row in the database
     * @param dataSource the datasource
     * @return Element
     * @throws SQLException  if any error
     * @throws IOException   if any error
     * @throws JDOMException if any error
     */
    protected Element load(final long id, final DataSource dataSource)
            throws SQLException, IOException, JDOMException {
        // Create result set
        ResultSet resultSet = null;
        // Connect to database
        try (var connection = dataSource.getConnection();
             // Create SQL statement
             var preparedStatement =
                     connection.prepareStatement(SELECT_DEPARTMENTS_SQL)
        ) {
            var result = "";
            // Execute the statement
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            // Copy the results into result
            while (resultSet.next())
                result = resultSet.getString(DEPARTMENT_COLUMN_LABEL);
            // Return the departments as an XML element
            return stringToElement(result);
        } finally {
            if (resultSet != null)
                resultSet.close();
        }
    }

    /**
     * Serialize the customer's departments to a CLOB and add it to the database
     *
     * @param dataSource the datasource
     * @return the id of the inserted row
     * @throws SQLException if any error occurs
     */
    protected long insert(final DataSource dataSource) throws SQLException {
        // Connect to database
        try (var connection = dataSource.getConnection();
             // Create SQL statement
             var preparedStatement =
                     connection.prepareStatement(INSERT_TO_CUSTOMERS_SQL)
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3,
                    elementToString(departmentsToXMLElement())); // Serialize the departments
            // Execute the statement
            preparedStatement.executeUpdate();
            LOGGER.info(String.format("Insert: name = %s  xmlString = %s",
                    name, elementToString(departmentsToXMLElement())));
            return id;
        }
    }

    /**
     * Serialize the departments field to an XMLElement.
     *
     * @return serialized XMLElement
     */
    protected Element departmentsToXMLElement() {
        var root = new Element(XML_ELEMENT_ROOT);
        for (Department department : departments)
            root.addContent(department.toXmlElement());
        return root;
    }

    /**
     * Deserialize the XMLElement to individual department objects
     *
     * @param source element source
     */
    void readDepartments(final Element source) {
        departments.clear();
        for (Element element : source.getChildren(XML_ELEMENT_CHILD_NAME))
            departments.add(Department.readXml(element));
    }
}
