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
package com.iluwatar.metamapping;

import com.iluwatar.metamapping.model.User;
import com.iluwatar.metamapping.service.UserService;
import com.iluwatar.metamapping.utils.DatabaseUtil;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.ServiceRegistry;

/**
 * Metadata Mapping specifies the mapping
 * between classes and tables so that
 * we could treat a table of any database like a Java class.
 *
 * <p>With hibernate, we achieve list/create/update/delete/get operations:
 * 1)Create the H2 Database in {@link DatabaseUtil}.
 * 2)Hibernate resolve hibernate.cfg.xml and generate service like save/list/get/delete.
 *    For learning metadata mapping pattern, we go deeper into Hibernate here:
 * a)read properties from hibernate.cfg.xml and mapping from *.hbm.xml
 * b)create session factory to generate session interacting with database
 * c)generate session with factory pattern
 * d)create query object or use basic api with session,
 *      hibernate will convert all query to database query according to metadata
 * 3)We encapsulate hibernate service in {@link UserService} for our use.
 * @see org.hibernate.cfg.Configuration#configure(String)
 * @see org.hibernate.cfg.Configuration#buildSessionFactory(ServiceRegistry)
 * @see org.hibernate.internal.SessionFactoryImpl#openSession()
 */
@Slf4j
public class App {
  /**
   * Program entry point.
   *
   * @param args command line args.
   */
  public static void main(String[] args) {
    // get service
    var userService = new UserService();
    // use create service to add users
    for (var user : generateSampleUsers()) {
      var id = userService.createUser(user);
      LOGGER.info("Add user" + user + "at" + id + ".");
    }
    // use list service to get users
    var users = userService.listUser();
    LOGGER.info(String.valueOf(users));
    // use get service to get a user
    var user = userService.getUser(1);
    LOGGER.info(String.valueOf(user));
    // change password of user 1
    user.setPassword("new123");
    // use update service to update user 1
    userService.updateUser(1, user);
    // use delete service to delete user 2
    userService.deleteUser(2);
    // close service
    userService.close();
  }

  /**
   * Generate users.
   *
   * @return list of users.
   */
  public static List<User> generateSampleUsers() {
    final var user1 = new User("ZhangSan", "zhs123");
    final var user2 = new User("LiSi", "ls123");
    final var user3 = new User("WangWu", "ww123");
    return List.of(user1, user2, user3);
  }
}