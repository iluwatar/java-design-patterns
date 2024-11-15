---
title: "Active Record Pattern in Java: A straightforward coupling of object design to database design"
shortTitle: Active Record
description: "Learn how the Active Record design pattern in Java simplifies data access and abstraction by coupling of object design to database design. Ideal for Java developers seeking a quick solution to data management in smaller-scale applications."
category: Architectural
language: en
tag:
    - Data access
    - Decoupling
    - Persistence
---


## Intent of Active Record Design Pattern

The Active Record design pattern encapsulates database access within an object that represents a row in a database table or view. 

This pattern simplifies data management by coupling object design directly to database design, making it ideal for smaller-scale applications.


## Detailed Explanation of Active Record Pattern with Real-World Examples

Real-world example

> Imagine managing an online store and having each product stored as a row inside a spreadsheet; unlike a typical spreadsheet, using the active record pattern not only lets you add information about the products on each row (such as pricing, quantity etc.), but also allows you to attach to each of these products capabilities over themselves, such as updating their quantity or their price and even properties over the whole spreadsheet, such as finding a different product by its ID. 

In plain words

> The Active Record pattern enables each row to have certain capabilities over itself, not just store data. Active Record combines data and behavior, making it easier for developers to manage database records in an object-oriented way.

Wikipedia says

> In software engineering, the active record pattern is an architectural pattern. It is found in software that stores in-memory object data in relational databases. The interface of an object conforming to this pattern would include functions such as Insert, Update, and Delete, plus properties that correspond more or less directly to the columns in the underlying database table.

## Programmatic Example of Active Record Pattern in Java

Let's first look at the user entity that we need to persist.


```java

public class User {
    
    private Integer id;
    private String name;
    private String email;
    
    public User(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
```

For convenience, we are storing the database configuration logic inside the same User class:

```java
    
     // Credentials for in-memory H2 database.

    private static final String JDBC_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";
    
    // Establish a database connection.

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }
    
     // Initialize the table (required each time program runs
    // as we are using an in-memory DB solution).

    public static void initializeTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT)";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
```

After configuring the database, our User class will contain methods thar mimic the typical CRUD operations performed on a database entry:

```java

/**
 * Insert a new record into the database.
 */

public void save() throws SQLException {
    String sql;
    if (this.id == null) { // New record
        sql = "INSERT INTO users(name, email) VALUES(?, ?)";
    } else { // Update existing record
        sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";
    }
    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        pstmt.setString(1, this.name);
        pstmt.setString(2, this.email);
        if (this.id != null) {
            pstmt.setInt(3, this.id);
        }
        pstmt.executeUpdate();
        if (this.id == null) {
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.id = generatedKeys.getInt(1);
                }
            }
        }
    }
}

/**
 * Find a user by ID.
 */

public static User findById(int id) throws SQLException {
    String sql = "SELECT * FROM users WHERE id = ?";
    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
        }
    }
    return null;
}
/**
 * Get all users.
 */

public static List<User> findAll() throws SQLException {
    String sql = "SELECT * FROM users";
    List<User> users = new ArrayList<>();
    try (Connection conn = connect();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        while (rs.next()) {
            users.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
        }
    }
    return users;
}

/**
 * Delete the user from the database.
 */

public void delete() throws SQLException {
    if (this.id == null) {
        return;
    }

    String sql = "DELETE FROM users WHERE id = ?";
    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, this.id);
        pstmt.executeUpdate();
        this.id = null;
    }
}
```

Finally, here is the Active Record Pattern in action: 

```java
public static void main(final String[] args) {
    try {
        // Initialize the database and create the users table if it doesn't exist
        User.initializeTable();
        LOGGER.info("Database and table initialized.");

        // Create a new user and save it to the database
        User user1 = new User(null, "John Doe", "john.doe@example.com");
        user1.save();
        LOGGER.info("New user saved: {} with ID {}", user1.getName(), user1.getId());

        // Retrieve and display the user by ID
        User foundUser = User.findById(user1.getId());
        if (foundUser != null) {
            LOGGER.info("User found: {} with email {}", foundUser.getName(), foundUser.getEmail());
        } else {
            LOGGER.info("User not found.");
        }

        // Update the user’s details
        assert foundUser != null;
        foundUser.setName("John Updated");
        foundUser.setEmail("john.updated@example.com");
        foundUser.save();
        LOGGER.info("User updated: {} with email {}", foundUser.getName(), foundUser.getEmail());

        // Retrieve all users
        List<User> users = User.findAll();
        LOGGER.info("All users in the database:");
        for (User user : users) {
            LOGGER.info("ID: {}, Name: {}, Email: {}", user.getId(), user.getName(), user.getEmail());
        }

        // Delete the user
        try {
            LOGGER.info("Deleting user with ID: {}", foundUser.getId());
            foundUser.delete();
            LOGGER.info("User successfully deleted!");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    } catch (SQLException e) {
        LOGGER.error("SQL error: {}", e.getMessage(), e);
    }
}
```

The program outputs:

```
19:34:53.731 [main] INFO com.iluwatar.activerecord.App -- Database and table initialized.
19:34:53.755 [main] INFO com.iluwatar.activerecord.App -- New user saved: John Doe with ID 1
19:34:53.759 [main] INFO com.iluwatar.activerecord.App -- User found: John Doe with email john.doe@example.com
19:34:53.762 [main] INFO com.iluwatar.activerecord.App -- User updated: John Updated with email john.updated@example.com
19:34:53.764 [main] INFO com.iluwatar.activerecord.App -- All users in the database:
19:34:53.764 [main] INFO com.iluwatar.activerecord.App -- ID: 1, Name: John Updated, Email: john.updated@example.com
19:34:53.764 [main] INFO com.iluwatar.activerecord.App -- Deleting user with ID: 1
19:34:53.768 [main] INFO com.iluwatar.activerecord.App -- User successfully deleted!
```

## When to Use the Active Record Pattern in Java

Use the Active Record pattern in Java when

* You need to simplify database interactions in an object-oriented way
* You want to reduce boilerplate code for basic database operations
* The database schema is relatively simple and relationships between tables are simple (like one-to-many or many-to-one relationships)
* Your app needs to fetch, manipulate, and save records frequently in a way that matches closely with the application's main logic

## Active Record Pattern Java Tutorials

* [A Beginner's Guide to Active Record](https://dev.to/jjpark987/a-beginners-guide-to-active-record-pnf)
* [Overview of the Active Record Pattern](https://blog.savetchuk.com/overview-of-the-active-record-pattern)

## Benefits and Trade-offs of Active Record Pattern

The active record pattern can a feasible choice for smaller-scale applications involving CRUD operations or prototyping quick database solutions. It is also a good pattern to transition to when dealing with the Transaction Script pattern.

On the other hand, it can bring about drawbacks regarding the risk of tight coupling, the lack of separation of concerns and performance constraints if working with large amounts of data, cases in which the Data Mapper pattern may be a more reliable option. 

## Related Java Design Patterns

* [Data Mapper](https://java-design-patterns.com/patterns/data-mapper/): Data Mapper pattern separates database logic entirely from business entities, promoting loose coupling.
* [Transaction Script](https://martinfowler.com/eaaCatalog/transactionScript.html/): Transaction Script focuses on procedural logic, organizing each transaction as a distinct script to handle business operations directly without embedding them in objects.


## References and Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [Head First Design Patterns: Building Extensible and Maintainable Object-Oriented Software](https://amzn.to/49NGldq)
* [J2EE Design Patterns](https://amzn.to/4dpzgmx)
* [Refactoring to Patterns](https://amzn.to/3VOO4F5)
