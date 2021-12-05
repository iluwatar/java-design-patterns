package com.iluwatar.daofactory;

/**
 * Interface that all UserDAOs must support
 *
 */

import java.util.Collection;

public interface UserDAO {
  /**
   * Insert user to the database.
   */
  int insertUser(User user);

  /**
   * Delete user from the database.
   */
  boolean deleteUser(User user);

  /**
   * Find user from the database.
   */
  User findUser(int userId);

  /**
   * Update user from the database.
   */
  boolean updateUser(User user);

  /**
   * Select collection of users from the database according to the criteria.
   */
  Collection selectUsersTO(String criteriaCol, String criteria);
}
