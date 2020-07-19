package com.ashishtrivedi16.transactionscript;

import com.ashishtrivedi16.transactionscript.db.HotelDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class Hotel {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionScriptApp.class);

    private HotelDAOImpl hotelDAO;

    public Hotel(HotelDAOImpl hotelDAO) {
        this.hotelDAO = hotelDAO;
    }

    public void bookRoom(int roomNumber) throws Exception {
        /*
            TODO
                -> Check if room is available
                -> Book the room
                -> Commit transaction
        */

        Optional<Room> room = hotelDAO.getById(roomNumber);

        if (!room.isPresent()) {
            LOGGER.info(roomNumber + " does not exist");
        } else {
            if (room.get().isBooked()) {
                LOGGER.info("Room already booked!");
            } else {
                Room updateRoomBooking = room.get();
                updateRoomBooking.setBooked(true);
                hotelDAO.update(updateRoomBooking);
            }
        }
    }

    public void cancelRoomBooking(int roomNumber) {
        /*

            TODO
                -> Check if room is booked
                -> Calculate refund price
                -> Cancel the room booking
                -> Commit transaction
         */
    }
}
