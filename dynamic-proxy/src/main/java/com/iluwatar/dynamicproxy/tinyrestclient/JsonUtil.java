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
package com.iluwatar.dynamicproxy.tinyrestclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * Utility class to handle Json operations.
 */
@Slf4j
public class JsonUtil {

  private static ObjectMapper objectMapper = new ObjectMapper();

  private JsonUtil() {
  }

  /**
   * Convert an object to a Json string representation.
   *
   * @param object Object to convert.
   * @param <T>    Object's class.
   * @return Json string.
   */
  public static <T> String objectToJson(T object) {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      LOGGER.error("Cannot convert the object " + object + " to Json.", e);
      return null;
    }
  }

  /**
   * Convert a Json string to an object of a class.
   *
   * @param json  Json string to convert.
   * @param clazz Object's class.
   * @param <T>   Object's generic class.
   * @return Object.
   */
  public static <T> T jsonToObject(String json, Class<T> clazz) {
    try {
      return objectMapper.readValue(json, clazz);
    } catch (IOException e) {
      LOGGER.error("Cannot convert the Json " + json + " to class " + clazz.getName() + ".", e);
      return null;
    }
  }

  /**
   * Convert a Json string to a List of objects of a class.
   *
   * @param json  Json string to convert.
   * @param clazz Object's class.
   * @param <T>   Object's generic class.
   * @return List of objects.
   */
  public static <T> List<T> jsonToList(String json, Class<T> clazz) {
    try {
      CollectionType listType = objectMapper.getTypeFactory()
          .constructCollectionType(ArrayList.class, clazz);
      return objectMapper.reader().forType(listType).readValue(json);
    } catch (JsonProcessingException e) {
      LOGGER.error("Cannot convert the Json " + json + " to List of " + clazz.getName() + ".", e);
      return List.of();
    }
  }

}
