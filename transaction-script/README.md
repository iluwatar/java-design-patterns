---
title: Transaction Script
category: Data access
language: en
tag:
    - Business
    - Data access
    - Domain
    - Persistence
    - Transactions
---

## Also known as

* Scripted Transactions

## Intent

Organize business logic by procedures where each procedure handles a single request from the presentation.

## Explanation

Real-world example

> An analogous real-world example of the Transaction Script design pattern can be seen in a bank teller's daily operations. Imagine a bank where each transaction, such as depositing money, withdrawing cash, or transferring funds between accounts, is handled by a specific script-like procedure. Each procedure takes in the necessary details (e.g., account numbers, amounts) and performs the transaction in a straightforward, step-by-step manner. This ensures that each transaction is processed correctly and independently, without the need for a complex system of rules and interactions. This simple, organized approach allows bank tellers to efficiently manage a variety of transactions throughout the day. 

In plain words

> Transaction Script organizes business logic into transactions that the system needs to carry out.

Wikipedia says

> The Transaction Script design pattern is a straightforward way to organize business logic in applications, particularly suitable for scenarios where each request from the presentation layer can be handled by a single procedure. This pattern is often used in simple applications or in systems where rapid development and ease of understanding are crucial. Each transaction script is responsible for a particular task, such as processing an order or calculating a result, and typically interacts directly with the database.

**Programmatic example**

Our programmatic example is about booking hotel rooms.

The `Hotel` class takes care of booking and cancelling room reservations.

```java
@Slf4j
public class Hotel {

  private final HotelDaoImpl hotelDao;

  public Hotel(HotelDaoImpl hotelDao) {
    this.hotelDao = hotelDao;
  }

  public void bookRoom(int roomNumber) throws Exception {

    Optional<Room> room = hotelDao.getById(roomNumber);

    if (room.isEmpty()) {
      throw new Exception("Room number: " + roomNumber + " does not exist");
    } else {
      if (room.get().isBooked()) {
        throw new Exception("Room already booked!");
      } else {
        Room updateRoomBooking = room.get();
        updateRoomBooking.setBooked(true);
        hotelDao.update(updateRoomBooking);
      }
    }
  }

  public void cancelRoomBooking(int roomNumber) throws Exception {

    Optional<Room> room = hotelDao.getById(roomNumber);

    if (room.isEmpty()) {
      throw new Exception("Room number: " + roomNumber + " does not exist");
    } else {
      if (room.get().isBooked()) {
        Room updateRoomBooking = room.get();
        updateRoomBooking.setBooked(false);
        int refundAmount = updateRoomBooking.getPrice();
        hotelDao.update(updateRoomBooking);

        LOGGER.info("Booking cancelled for room number: " + roomNumber);
        LOGGER.info(refundAmount + " is refunded");
      } else {
        throw new Exception("No booking for the room exists");
      }
    }
  }
}
```

The `Hotel` class has two methods, one for booking and cancelling a room respectively. Each one of them handles a single transaction in the system, making `Hotel` implement the Transaction Script pattern.

The `bookRoom` method consolidates all the needed steps like checking if the room is already booked or not, if not booked then books the room and updates the database by using the DAO. 

The `cancelRoom` method consolidates steps like checking if the room is booked or not, if booked then calculates the refund amount and updates the database using the DAO.

Here is the `App` class with `main` method for running the example.

```java
public class App {

  private static final String H2_DB_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) throws Exception {

    final var dataSource = createDataSource();
    deleteSchema(dataSource);
    createSchema(dataSource);
    final var dao = new HotelDaoImpl(dataSource);

    // Add rooms
    addRooms(dao);

    // Print room booking status
    getRoomStatus(dao);

    var hotel = new Hotel(dao);

    // Book rooms
    hotel.bookRoom(1);
    hotel.bookRoom(2);
    hotel.bookRoom(3);
    hotel.bookRoom(4);
    hotel.bookRoom(5);
    hotel.bookRoom(6);

    // Cancel booking for a few rooms
    hotel.cancelRoomBooking(1);
    hotel.cancelRoomBooking(3);
    hotel.cancelRoomBooking(5);

    getRoomStatus(dao);

    deleteSchema(dataSource);
  }

  private static void getRoomStatus(HotelDaoImpl dao) throws Exception {
    try (var customerStream = dao.getAll()) {
      customerStream.forEach((customer) -> LOGGER.info(customer.toString()));
    }
  }

  private static void deleteSchema(DataSource dataSource) throws java.sql.SQLException {
    try (var connection = dataSource.getConnection();
         var statement = connection.createStatement()) {
      statement.execute(RoomSchemaSql.DELETE_SCHEMA_SQL);
    }
  }

  private static void createSchema(DataSource dataSource) throws Exception {
    try (var connection = dataSource.getConnection();
         var statement = connection.createStatement()) {
      statement.execute(RoomSchemaSql.CREATE_SCHEMA_SQL);
    } catch (Exception e) {
      throw new Exception(e.getMessage(), e);
    }
  }

  private static DataSource createDataSource() {
    var dataSource = new JdbcDataSource();
    dataSource.setUrl(H2_DB_URL);
    return dataSource;
  }

  private static void addRooms(HotelDaoImpl hotelDao) throws Exception {
    for (var room : generateSampleRooms()) {
      hotelDao.add(room);
    }
  }

  private static List<Room> generateSampleRooms() {
    final var room1 = new Room(1, "Single", 50, false);
    final var room2 = new Room(2, "Double", 80, false);
    final var room3 = new Room(3, "Queen", 120, false);
    final var room4 = new Room(4, "King", 150, false);
    final var room5 = new Room(5, "Single", 50, false);
    final var room6 = new Room(6, "Double", 80, false);
    return List.of(room1, room2, room3, room4, room5, room6);
  }
}
```

The `App.java` file has the `main` entry point of the application. It demonstrates the use of the Transaction Script pattern in a hotel management context. Here's a step-by-step breakdown of what happens:

1. A new H2 database data source is created using the `createDataSource()` method. This data source is used to interact with the in-memory H2 database.

2. The existing schema (if any) in the database is deleted using the `deleteSchema(dataSource)` method. This is done to ensure a clean state before the application starts.

3. A new schema is created in the database using the `createSchema(dataSource)` method. The schema includes the necessary tables and relationships for the application.

4. An instance of `HotelDaoImpl` is created, which serves as the Data Access Object (DAO). This object is responsible for handling the database operations related to the hotel rooms.

5. Sample rooms are added to the hotel using the `addRooms(dao)` method. This method generates a list of sample rooms and adds them to the hotel via the DAO.

6. The booking status of all rooms is printed using the `getRoomStatus(dao)` method.

7. An instance of `Hotel` is created with the DAO as a parameter. This object represents the hotel and provides methods to book and cancel room bookings.

8. Several rooms are booked using the `hotel.bookRoom(roomNumber)` method.

9. Some of the bookings are then cancelled using the `hotel.cancelRoomBooking(roomNumber)` method.

10. The booking status of all rooms is printed again to reflect the changes.

11. Finally, the schema in the database is deleted again using the `deleteSchema(dataSource)` method, cleaning up the state of the database.

Console output:

```
14:22:20.050 [main] INFO com.iluwatar.transactionscript.App -- Room(id=1, roomType=Single, price=50, booked=false)
14:22:20.051 [main] INFO com.iluwatar.transactionscript.App -- Room(id=2, roomType=Double, price=80, booked=false)
14:22:20.051 [main] INFO com.iluwatar.transactionscript.App -- Room(id=3, roomType=Queen, price=120, booked=false)
14:22:20.051 [main] INFO com.iluwatar.transactionscript.App -- Room(id=4, roomType=King, price=150, booked=false)
14:22:20.051 [main] INFO com.iluwatar.transactionscript.App -- Room(id=5, roomType=Single, price=50, booked=false)
14:22:20.051 [main] INFO com.iluwatar.transactionscript.App -- Room(id=6, roomType=Double, price=80, booked=false)
14:22:20.058 [main] INFO com.iluwatar.transactionscript.Hotel -- Booking cancelled for room number: 1
14:22:20.058 [main] INFO com.iluwatar.transactionscript.Hotel -- 50 is refunded
14:22:20.059 [main] INFO com.iluwatar.transactionscript.Hotel -- Booking cancelled for room number: 3
14:22:20.059 [main] INFO com.iluwatar.transactionscript.Hotel -- 120 is refunded
14:22:20.059 [main] INFO com.iluwatar.transactionscript.Hotel -- Booking cancelled for room number: 5
14:22:20.059 [main] INFO com.iluwatar.transactionscript.Hotel -- 50 is refunded
14:22:20.060 [main] INFO com.iluwatar.transactionscript.App -- Room(id=1, roomType=Single, price=50, booked=false)
14:22:20.060 [main] INFO com.iluwatar.transactionscript.App -- Room(id=2, roomType=Double, price=80, booked=true)
14:22:20.060 [main] INFO com.iluwatar.transactionscript.App -- Room(id=3, roomType=Queen, price=120, booked=false)
14:22:20.060 [main] INFO com.iluwatar.transactionscript.App -- Room(id=4, roomType=King, price=150, booked=true)
14:22:20.060 [main] INFO com.iluwatar.transactionscript.App -- Room(id=5, roomType=Single, price=50, booked=false)
14:22:20.060 [main] INFO com.iluwatar.transactionscript.App -- Room(id=6, roomType=Double, price=80, booked=true)
```

This pattern is suitable for simple business logic and can be easily understood and maintained.

## Applicability

* Use when business logic is simple and can be easily organized into individual procedures.
* Suitable for applications with simple transaction requirements or where the logic doesn't justify complex architectures like Domain Model.

## Tutorials

* [Transaction Script Pattern (DZone)](https://dzone.com/articles/transaction-script-pattern#:~:text=Transaction%20Script%20(TS)%20is%20the,need%20big%20architecture%20behind%20them.)
* [Transaction Script (InformIT)](https://www.informit.com/articles/article.aspx?p=1398617)

### Known Uses

* Early-stage startups and small-scale applications where rapid development is crucial.
* Enterprise applications with well-defined procedures like banking transactions or e-commerce order processing.
* Legacy systems where business logic is already written as scripts.

## Consequences

Benefits:

* Simple and straightforward to implement.
* Easy to understand and maintain for straightforward business logic.
* Fast development cycle for small applications.

Trade-offs:

* Can lead to duplicated code if not carefully managed.
* Not suitable for complex business logic; can become unmanageable as the application grows.
* Harder to test in isolation compared to more structured approaches like Domain Model.

## Related patterns

* [Domain Model](https://java-design-patterns.com/patterns/domain-model/): Unlike Transaction Script, Domain Model organizes business logic around the data model and is better suited for complex business rules.
* [Service Layer](https://java-design-patterns.com/patterns/service-layer/): Often used together with Transaction Script to define an application's boundary and encapsulate the business logic.
* [Table Module](https://java-design-patterns.com/patterns/table-module/): Similar to Transaction Script but organizes logic using a single class per table rather than a procedure per request.

## Credits

* [Enterprise Integration Patterns: Designing, Building, and Deploying Messaging Solutions](https://amzn.to/3WcFVui)
* [Patterns of Enterprise Application Architecture](https://amzn.to/3WfKBPR)
