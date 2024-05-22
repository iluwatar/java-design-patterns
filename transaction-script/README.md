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

## Class diagram

![Transaction Script](./etc/transaction-script.png "Transaction Script")

## Applicability

* Use when business logic is simple and can be easily organized into individual procedures.
* Suitable for applications with simple transaction requirements or where the logic doesn't justify complex architectures like Domain Model.

## Tutorials

* [Transaction Script Pattern - DZone](https://dzone.com/articles/transaction-script-pattern#:~:text=Transaction%20Script%20(TS)%20is%20the,need%20big%20architecture%20behind%20them.)
* [Transaction Script](https://www.informit.com/articles/article.aspx?p=1398617)

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
