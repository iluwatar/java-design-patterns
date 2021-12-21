package com.iluwatar.metamapping;

import com.iluwatar.metamapping.model.User;
import com.iluwatar.metamapping.service.UserService;
import com.iluwatar.metamapping.utils.DatabaseUtil;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * Metadata Mapping specifies the mapping
 * between classes and tables so that
 * we could treat a table of any database like a Java class.
 *
 * With hibernate, we achieve list/create/update/delete/get operations.
 * Let's have a test!
 */
@Slf4j
public class App {
  /**
   * Program entry point.
   *
   * @param args command line args.
   * @throws Exception if any error occurs.
   */
  public static void main(String[] args) {
    DatabaseUtil.createDataSource();
    UserService userService = new UserService();
    for (User user: generateSampleUsers()) {
      userService.createUser(user);
    }
    List<User> users = userService.listUser();
    LOGGER.info(String.valueOf(users));
    User user = userService.getUser(1);
    LOGGER.info(String.valueOf(user));
    user.setPassword("111111");
    userService.updateUser(1, user);
    userService.deleteUser(2);
    userService.close();
  }

  /**
   * Generate users.
   *
   * @return list of users.
   */
  public static List<User> generateSampleUsers() {
    final var user1 = new User("A", "aaaaaa");
    final var user2 = new User("B", "bbbbbb");
    final var user3 = new User("C", "cccccc");
    return List.of(user1, user2, user3);
  }
}
