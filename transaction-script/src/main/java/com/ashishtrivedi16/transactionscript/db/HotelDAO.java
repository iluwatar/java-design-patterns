package com.ashishtrivedi16.transactionscript.db;

import com.ashishtrivedi16.transactionscript.Room;

import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Stream;

public interface HotelDAO {

    public Stream<Room> getAll() throws Exception;

    public Optional<Room> getById(int id) throws Exception;

    public Boolean add(Room room) throws SQLException, Exception;

    public Boolean update(Room room) throws Exception;

    public Boolean delete(Room room) throws Exception;
}
