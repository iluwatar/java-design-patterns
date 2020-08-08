---
layout: pattern
title: Transaction Script
folder: transaction-script
permalink: /patterns/transaction-script/
categories: Behavioral
tags:
 - Data access
---

## Intent
Transaction Script organizes business logic by procedures where each procedure handles a single request from the presentation.

## Explanation
Real world example
> Your need is to be able to book a hotel room and also be able to cancel that booking.
> 

In plain words
> All logic related to booking a hotel room like checking room availability,
> calculate rates and update the database is done inside a single transaction script.
> Similar procedure is also needed for cancelling a room booking and all 
> that logic will be in another transaction script.

Programmatic example

The Hotel class takes care of booking and cancelling a room in a hotel.

```java
public class Hotel {
  private static final Logger LOGGER = LoggerFactory.getLogger(TransactionScriptApp.class);

  private HotelDaoImpl hotelDao;

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

This class has two methods, one for booking and cancelling a room respectively.

```
public void bookRoom(int roomNumber);
```
The book room method consolidates all the needed steps like checking if the room is already booked
or not, if not booked then books the rooma nd updates the database by using the DAO. 

```
public void cancelRoomBooking(int roomNumber)
```
The cancel room method consolidates steps like checking if the room is booked or not, 
if booked then calculates the refund amount and updates the database using the DAO.

## Class diagram
![alt text](./etc/transaction-script.png "Transaction script model")

## Applicability
Use the transaction script model when the application has only a small amount of logic and that
logic won't be extended in the future.


## Known uses
* Revenue recognition in business systems.

## Consequences
* As the business logic gets more complicated, 
it gets progressively harder to keep the transaction script 
in a well-designed state.
* Code duplication between transaction scripts can occur.
* Normally not easy to refactor transactions script to other domain logic
patterns.

## Related patterns
* Domain Model
* Table Module
* Service Layer

## Credits
* [Transaction Script Pattern](https://dzone.com/articles/transaction-script-pattern#:~:text=Transaction%20Script%20(TS)%20is%20the,need%20big%20architecture%20behind%20them.)
* [Transaction Script](https://www.informit.com/articles/article.aspx?p=1398617)
* [Patterns of Enterprise Application Architecture](https://www.amazon.com/gp/product/0321127420?ie=UTF8&tag=gupesasnebl-20&linkCode=as2&camp=1789&creative=9325&creativeASIN=0321127420)
