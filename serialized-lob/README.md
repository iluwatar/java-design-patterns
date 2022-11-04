--- 
layout: pattern
title: Serialized LOB
folder: serialized-lob
permalink: /patterns/serialized-lob/
categories: Behavioral
language: en
tags:
- Gang of Four
- Data Access
---

## Intent
The serialized LOB pattern is used to save a graph of objects by serializing them into a 
single large object, which it then stores in a database field. The serialization can be 
done as either a binary serialization (BLOB) or a textual character serializaion (CLOB).

## Explanation

Real world example

> I wish to store the information from a complicated graph of small objects such as a family tree
> in a relational schema. Much of the important information in these structures is in the links 
> between them and not the actual objects themselves. Accessing information such as ancestors, 
> siblings, descendants, etc. can be very difficult in a relational schema. It will require a 
> series of join operations which are often slow and awkward. Persisting this information as one large
> object (LOB) can allow us to write simple methods to access this information. 

In Pain Words

> It provides an easy way to access and understand information that is typically hard to access and difficult to 
> understand.

Programmatic Example

Let's take an example of a department hierarchy. Consider a situation where a customer can be linked to 
any number of departments. Each department can then have a list of subsidiary departments. Storing this
information in a typical relation schema would quickly become very confusing. However, we can serialize 
all the departments into one XML CLOB and store this in the database to make it easier to understand. 
First we have our department class:

```java
public class Department {

    private final String name;
    private final ArrayList<Department> subsidiaries = new ArrayList<>();
    
    protected Department(final String name) {
        this.name = name;
    }

    protected ArrayList<Department> getSubsidiaries() {
        return subsidiaries;
    }
}
```

We also have our customer class

```java
public class Customer {
    private final long id;
    private final String name;
    private final ArrayList<Department> departments = new ArrayList<>();
    
    protected Customer(final String name, final long id) {
        this.name = name;
        this.id = id;
    }

    protected ArrayList<Department> getDepartments() {
        return departments;
    }
}
```

A Department object can be serialized or deserialized using toXmlElement() and readXml respectively:

```java
protected static Department readXml(final Element source) {
    // Deserialize source department
    var name = source.getAttributeValue(XML_ELEMENT_NAME);
    var result = new Department(name);
    // Deserialize subsidiary departments
    for (Element element : source.getChildren(XML_ELEMENT_DEPARTMENT))
        result.subsidiaries.add(readXml(element));
    return result;
    }

protected Element toXmlElement() {
        // Serialize the root department
        var root = new Element(XML_ELEMENT_DEPARTMENT);
        root.setAttribute(XML_ELEMENT_NAME, name);
        // Serialize subsidiary departments
        for (Department dep : subsidiaries)
            root.addContent(dep.toXmlElement());
        return root;
        }
```

Finally, a customer object can then be inserted or loaded from a database using the following methods:

```java
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

protected static String elementToString(final Element element) {
    var xmlOutput = new XMLOutputter();
    return xmlOutput.outputString(element);
}

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
        
protected Element departmentsToXMLElement() {
    var root = new Element(XML_ELEMENT_ROOT);
    for (Department department : departments)
        root.addContent(department.toXmlElement());
    return root;
}

void readDepartments(final Element source) {
    departments.clear();
    for (Element element : source.getChildren(XML_ELEMENT_CHILD_NAME))
        departments.add(Department.readXml(element));
}
```
Now, let's use the pattern:

```java
// Initialize customer and department objects
var customer = new Customer("customer1", 1);
var department1 = new Department("department1");
var department2 = new Department("department2");
var department3 = new Department("department3");
department1.getSubsidiaries().add(department2);
department2.getSubsidiaries().add(department3);
customer.getDepartments().add(department1);

// Insert the customer into the database
var id = customer.insert(dataSource);
// Load the customer from the database as an XML element
var element = customer.load(id, dataSource);
LOGGER.info(Customer.elementToString(customer.departmentsToXMLElement()));
customer.readDepartments(element);
LOGGER.info(Customer.elementToString(customer.departmentsToXMLElement()));
```

Program output: 

```java
<departmentList><department name="department1"><department name="department2"><department name="department3" /></department></department></departmentList>
<departmentList><department name="department1"><department name="department2"><department name="department3" /></department></department></departmentList>
```

## Class diagram

## Applicability

Use the serialized LOB when:

* You wish to store information about the links between a graph of small objects in a relational schema
* You wish to store hierarchical information in a relational schema

## Related patterns

* memento

## Credits

* [Patterns of Enterprise Application Architecture] (http://www.bizoutlook.by/pdf/Patterns_of_Enterprise_Application_Architecture.pdf)