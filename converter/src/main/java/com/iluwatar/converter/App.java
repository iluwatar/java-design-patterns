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

package com.iluwatar.converter;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * The Converter pattern is a behavioral design pattern which allows a common way of bidirectional
 * conversion between corresponding types (e.g. DTO and domain representations of the logically
 * isomorphic types). Moreover, the pattern introduces a common way of converting a collection of
 * objects between types.
 */
@Slf4j
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    Converter<UserDto, User> userConverter = new UserConverter();

    UserDto dtoUser = new UserDto("John", "Doe", true, "whatever[at]wherever.com");
    User user = userConverter.convertFromDto(dtoUser);
    LOGGER.info("Entity converted from DTO: {}", user);

    var users = List.of(
        new User("Camile", "Tough", false, "124sad"),
        new User("Marti", "Luther", true, "42309fd"),
        new User("Kate", "Smith", true, "if0243")
    );
    LOGGER.info("Domain entities:");
    users.stream().map(User::toString).forEach(LOGGER::info);

    LOGGER.info("DTO entities converted from domain:");
    List<UserDto> dtoEntities = userConverter.createFromEntities(users);
    dtoEntities.stream().map(UserDto::toString).forEach(LOGGER::info);

  }
}
