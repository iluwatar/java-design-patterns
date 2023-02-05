/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.caching.database;

import com.iluwatar.caching.UserAccount;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of DatabaseManager.
 * implements base methods to work with hashMap as database.
 */
public class VirtualDb implements DbManager {
  /**
   * Virtual DataBase.
   */
  private Map<String, UserAccount> db;

  /**
   * Creates new HashMap.
   */
  @Override
  public void connect() {
    db = new HashMap<>();
  }

  @Override
  public void disconnect() {
    db = null;
  }

  /**
   * Read from Db.
   *
   * @param userId {@link String}
   * @return {@link UserAccount}
   */
  @Override
  public UserAccount readFromDb(final String userId) {
    if (db.containsKey(userId)) {
      return db.get(userId);
    }
    return null;
  }

  /**
   * Write to DB.
   *
   * @param userAccount {@link UserAccount}
   * @return {@link UserAccount}
   */
  @Override
  public UserAccount writeToDb(final UserAccount userAccount) {
    db.put(userAccount.getUserId(), userAccount);
    return userAccount;
  }

  /**
   * Update reecord in DB.
   *
   * @param userAccount {@link UserAccount}
   * @return {@link UserAccount}
   */
  @Override
  public UserAccount updateDb(final UserAccount userAccount) {
    return writeToDb(userAccount);
  }

  /**
   * Update.
   *
   * @param userAccount {@link UserAccount}
   * @return {@link UserAccount}
   */
  @Override
  public UserAccount upsertDb(final UserAccount userAccount) {
    return updateDb(userAccount);
  }
}
