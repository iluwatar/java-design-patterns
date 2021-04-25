/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.tablemodule;

import java.util.Optional;
import java.util.stream.Stream;

public interface RoomDao {

  /**
   * Select all rooms from the database.
   *
   * @return room stream
   * @throws Exception if any error
   */
  Stream<Room> getAll() throws Exception;

  /**
   * Select a room from the database by id.
   *
   * @param id the if of the room
   * @return A instance of room or null
   * @throws Exception if any error
   */
  Optional<Room> getById(int id) throws Exception;

  /**
   * Add a room to the database.
   *
   * @param room the room to be added
   * @return the status of this operation
   * @throws Exception if any error
   */
  Boolean add(Room room) throws Exception;

  /**
   * Update the room information in the database.
   *
   * @param room the room to be updated
   * @return the status of this operation
   * @throws Exception if any error
   */
  Boolean update(Room room) throws Exception;

  /**
   * Delete a room from the database.
   *
   * @param room the room to be deleted
   * @return the status of this operation
   * @throws Exception if any error
   */
  Boolean delete(Room room) throws Exception;
}
