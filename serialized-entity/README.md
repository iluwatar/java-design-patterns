---
title: Serialized Entity
categories: Data access
language: en
tag:
    - Data access
    - Data transfer
    - Persistence
---

## Also known as

* Object Serialization

## Intent

Enable easy conversion of Java objects to and from a serialized format, allowing them to be easily stored and transferred.

## Explanation

Real-world example

> An example of the Serialized Entity design pattern in the real world can be found in the process of saving and loading game state in video games. When a player decides to save their progress, the current state of the game, including the player's position, inventory, and achievements, is serialized into a file format such as JSON or binary. This file can then be stored on the player's device or on a cloud server. When the player wants to resume their game, the serialized data is deserialized back into the game's objects, allowing the player to continue from where they left off. This mechanism ensures that complex game states can be easily saved and restored, providing a seamless gaming experience.

In plain words

> The Serialized Entity design pattern enables the conversion of Java objects to and from a serialized format for easy storage and transfer.

Wikipedia says

> In computing, serialization is the process of translating a data structure or object state into a format that can be stored (e.g. files in secondary storage devices, data buffers in primary storage devices) or transmitted (e.g. data streams over computer networks) and reconstructed later (possibly in a different computer environment). When the resulting series of bits is reread according to the serialization format, it can be used to create a semantically identical clone of the original object. For many complex objects, such as those that make extensive use of references, this process is not straightforward. Serialization of objects does not include any of their associated methods with which they were previously linked.

**Programmatic Example**

The Serialized Entity design pattern is a way to easily persist Java objects to the database. It uses the `Serializable` interface and the DAO (Data Access Object) pattern. The pattern first uses `Serializable` to convert a Java object into a set of bytes, then it uses the DAO pattern to store this set of bytes as a BLOB (Binary Large OBject) in the database.

First, we have the `Country` class, which is a simple POJO (Plain Old Java Object) that represents the data that will be serialized and stored in the database. It implements the `Serializable` interface, which means it can be converted to a byte stream and restored from it.

```java
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class Country implements Serializable {

    private int code;
    private String name;
    private String continents;
    private String language;
    @Serial
    private static final long serialVersionUID = 7149851;
}
```

Next, we have the `CountryDao` interface, which defines the methods for persisting and retrieving `Country` objects from the database.

```java
public interface CountryDao {
    int insertCountry() throws IOException;
    int selectCountry() throws IOException, ClassNotFoundException;
}
```

The `CountrySchemaSql` class implements the `CountryDao` interface. It uses a `DataSource` to connect to the database and a `Country` object that it will serialize and store in the database.

```java
@Slf4j
public class CountrySchemaSql implements CountryDao {
    
    public static final String CREATE_SCHEMA_SQL = "CREATE TABLE IF NOT EXISTS WORLD (ID INT PRIMARY KEY, COUNTRY BLOB)";
    public static final String DELETE_SCHEMA_SQL = "DROP TABLE WORLD IF EXISTS";

    private Country country;
    private DataSource dataSource;

    public CountrySchemaSql(Country country, DataSource dataSource) {
        this.country = new Country(
                country.getCode(),
                country.getName(),
                country.getContinents(),
                country.getLanguage()
        );
        this.dataSource = dataSource;
    }

    @Override
    public int insertCountry() throws IOException {
        var sql = "INSERT INTO WORLD (ID, COUNTRY) VALUES (?, ?)";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql);
             ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oss = new ObjectOutputStream(baos)) {

            oss.writeObject(country);
            oss.flush();

            preparedStatement.setInt(1, country.getCode());
            preparedStatement.setBlob(2, new ByteArrayInputStream(baos.toByteArray()));
            preparedStatement.execute();
            return country.getCode();
        } catch (SQLException e) {
            LOGGER.info("Exception thrown " + e.getMessage());
        }
        return -1;
    }

    @Override
    public int selectCountry() throws IOException, ClassNotFoundException {
        var sql = "SELECT ID, COUNTRY FROM WORLD WHERE ID = ?";
        try (var connection = dataSource.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, country.getCode());

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    Blob countryBlob = rs.getBlob("country");
                    ByteArrayInputStream baos = new ByteArrayInputStream(countryBlob.getBytes(1, (int) countryBlob.length()));
                    ObjectInputStream ois = new ObjectInputStream(baos);
                    country = (Country) ois.readObject();
                    LOGGER.info("Country: " + country);
                }
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            LOGGER.info("Exception thrown " + e.getMessage());
        }
        return -1;
    }
}
```

Finally, in the `App` class, we create `Country` objects and `CountrySchemaSql` objects. We then call the `insertCountry` method to serialize the `Country` objects and store them in the database. We also call the `selectCountry` method to retrieve the serialized `Country` objects from the database and deserialize them back into `Country` objects.

```java
public static void main(String[] args) throws IOException, ClassNotFoundException {
    final var dataSource = createDataSource();

    deleteSchema(dataSource);
    createSchema(dataSource);

    final var China = new Country(86, "China", "Asia", "Chinese");
    final var UnitedArabEmirates = new Country(971, "United Arab Emirates", "Asia", "Arabic");

    final var serializedChina = new CountrySchemaSql(China, dataSource);
    final var serializedUnitedArabEmirates = new CountrySchemaSql(UnitedArabEmirates, dataSource);

    serializedChina.insertCountry();
    serializedUnitedArabEmirates.insertCountry();

    serializedChina.selectCountry();
    serializedUnitedArabEmirates.selectCountry();
}
```

Console output:

```
11:55:32.842 [main] INFO com.iluwatar.serializedentity.CountrySchemaSql -- Country: Country(code=86, name=China, continents=Asia, language=Chinese)
11:55:32.870 [main] INFO com.iluwatar.serializedentity.CountrySchemaSql -- Country: Country(code=971, name=United Arab Emirates, continents=Asia, language=Arabic)
```

This is a basic example of the Serialized Entity design pattern. It shows how to serialize Java objects, store them in a database, and then retrieve and deserialize them.

## Applicability

* Use when you need to persist the state of an object or transfer objects between different tiers of an application.
* Useful in scenarios where objects need to be shared over a network or saved to a file.

## Known Uses

* Java's built-in serialization mechanism using `Serializable` interface.
* Storing session data in web applications.
* Caching objects to improve performance.
* Transferring objects in distributed systems using RMI or other RPC mechanisms.

## Consequences

Benefits:

* Simplifies the process of saving and restoring object state.
* Facilitates object transfer across different parts of a system.
* Reduces boilerplate code for manual serialization and deserialization.

Trade-offs:

* Can introduce security risks if not handled properly (e.g., deserialization attacks).
* Serialized formats may not be easily readable or editable by humans.
* Changes to the class structure may break compatibility with previously serialized data.

## Related Patterns

* [Data Transfer Object (DTO)](https://java-design-patterns.com/patterns/data-transfer-object/): Used to encapsulate data and send it over the network. Often serialized for transmission.
* [Memento](https://java-design-patterns.com/patterns/memento/): Provides a way to capture and restore an object's state, often using serialization.
* [Proxy](https://java-design-patterns.com/patterns/proxy/): Proxies can serialize requests to interact with remote objects.

## Credits

* [Design Patterns: Elements of Reusable Object-Oriented Software](https://amzn.to/3w0pvKI)
* [Effective Java](https://amzn.to/4cGk2Jz)
* [J2EE Design Patterns](https://amzn.to/4dpzgmx)
* [Java Concurrency in Practice](https://amzn.to/4aRMruW)
