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

package com.iluwatar.commander;

import com.iluwatar.commander.exceptions.DatabaseUnavailableException;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Service class is an abstract class extended by all services in this example. They all have a
 * public receiveRequest method to receive requests, which could also contain details of the user
 * other than the implementation details (though we are not doing that here) and updateDb method
 * which adds to their respective databases. There is a method to generate transaction/request id
 * for the transactions/requests, which are then sent back. These could be stored by the {@link
 * Commander} class in a separate database for reference (though we are not doing that here).
 */

public abstract class Service {

  protected final Database database;
  public ArrayList<Exception> exceptionsList;
  private static final SecureRandom RANDOM = new SecureRandom();
  private static final String ALL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
  private static final Hashtable<String, Boolean> USED_IDS = new Hashtable<>();

  protected Service(Database db, Exception... exc) {
    this.database = db;
    this.exceptionsList = new ArrayList<>(List.of(exc));
  }

  public abstract String receiveRequest(Object... parameters) throws DatabaseUnavailableException;

  protected abstract String updateDb(Object... parameters) throws DatabaseUnavailableException;

  protected String generateId() {
    StringBuilder random = new StringBuilder();
    while (random.length() < 12) { // length of the random string.
      int index = (int) (RANDOM.nextFloat() * ALL_CHARS.length());
      random.append(ALL_CHARS.charAt(index));
    }
    String id = random.toString();
    if (USED_IDS.get(id) != null) {
      while (USED_IDS.get(id)) {
        id = generateId();
      }
    }
    return id;
  }
}
