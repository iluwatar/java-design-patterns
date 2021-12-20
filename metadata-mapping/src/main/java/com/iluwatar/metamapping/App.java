package com.iluwatar.metamapping;

import com.iluwatar.metamapping.model.User;
import com.iluwatar.metamapping.service.UserService;
import com.iluwatar.metamapping.utils.DatabaseUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class App {
    public static void main(String[] args){
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
