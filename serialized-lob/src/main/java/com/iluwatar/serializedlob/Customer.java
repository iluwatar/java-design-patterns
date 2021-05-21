package com.iluwatar.serializedlob;

import java.io.IOException;
import java.io.StringReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;


@Slf4j
public class Customer {
  /**
   * Public element for creating schema.
   */
  public static final String CREATE_SCHEMA_SQL =
          "CREATE TABLE IF NOT EXISTS CUSTOMERS (ID NUMBER UNIQUE, NAME"
                  + " VARCHAR(30),DEPARTMENTS VARCHAR)";
  /**
   * Public element for deleting schema.
   */
  public static final String DELETE_SCHEMA_SQL = "DROP TABLE CUSTOMERS IF "
          + "EXISTS";

  /**
   * Private filed id.
   */
  private final long id;

  /**
   * Private field name.
   */
  private final String name;

  /**
   * Private filed departments.
   */
  private final ArrayList<Department> departments = new ArrayList<>();

  /**
   * Protected constructor.
   *
   * @param name1 name
   * @param id1   id
   */
  protected Customer(final String name1, final long id1) {
    this.name = name1;
    this.id = id1;
  }

  protected static Element stringToElement(final String xmlstr)
          throws IOException, JDOMException {
    var stringReader = new StringReader(xmlstr);
    var builder = new SAXBuilder();
    var doc = builder.build(stringReader);
    var elem = doc.getRootElement();
    return elem.detach();
  }

  protected static String elementToString(final Element element) {
    var xmlOutput = new XMLOutputter();
    return xmlOutput.outputString(element);
  }

  /**
   * Load a element from the database.
   *
   * @param id1        id of the query
   * @param dataSource the datasource
   * @return Element
   * @throws SQLException  if any error
   * @throws IOException   if any error
   * @throws JDOMException if any error
   */
  protected Element load(final long id1, final DataSource dataSource)
          throws SQLException, IOException, JDOMException {
    var sql = "select departments from customers where id = ?";
    ResultSet resultSet = null;
    try (var connection = dataSource.getConnection();
         var preparedStatement =
                 connection.prepareStatement(sql)
    ) {
      var result = "";
      preparedStatement.setLong(1, id1);
      resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        result = resultSet.getString("departments");
      }
      return stringToElement(result);
    } finally {
      if (resultSet != null) {
        resultSet.close();
      }
    }
  }

  /**
   * Insert LOB to database.
   *
   * @param dataSource the datasource
   * @return the id of the inserted row
   * @throws SQLException if any error
   */
  protected long insert(final DataSource dataSource) throws SQLException {
    var sql = "insert into customers (id,name, departments) values (?,?,?)";
    try (var connection = dataSource.getConnection();
         var preparedStatement =
                 connection.prepareStatement(sql)
    ) {
      preparedStatement.setLong(1, id);
      preparedStatement.setString(2, name);
      preparedStatement.setString(3,
              elementToString(departmentsToXmlElement()));
      preparedStatement.executeUpdate();
      LOGGER.info(String.format("Insert: name = %s  xmlString = %s",
              name, elementToString(departmentsToXmlElement())));
      return id;
    }
  }

  /**
   * Add element to departments.
   *
   * @param department the department to be added
   */
  protected void addDepartment(final Department department) {
    departments.add(department);

  }

  /**
   * Convert filed departments to XmlElement.
   *
   * @return element
   */
  protected Element departmentsToXmlElement() {
    var root = new Element("departmentList");
    for (Department dep : departments) {
      root.addContent(dep.toXmlElement());
    }
    return root;
  }

  /**
   * Read element.
   *
   * @param source element source
   */
  void readDepartments(final Element source) {
    departments.clear();
    for (Element element : source.getChildren("department")) {
      addDepartment(Department.readXml(element));
    }
  }

  /**
   * The get method of the field departments.
   *
   * @return departments
   */
  protected ArrayList<Department> getDepartments() {
    return departments;
  }
}
