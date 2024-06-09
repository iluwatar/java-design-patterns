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
package com.iluwatar.tolerantreader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import lombok.NoArgsConstructor;

/**
 * RainbowFishSerializer provides methods for reading and writing {@link RainbowFish} objects to
 * file. Tolerant Reader pattern is implemented here by serializing maps instead of {@link
 * RainbowFish} objects. This way the reader does not break even though new properties are added to
 * the schema.
 */
@NoArgsConstructor
public final class RainbowFishSerializer {

  public static final String LENGTH_METERS = "lengthMeters";
  public static final String WEIGHT_TONS = "weightTons";

  /**
   * Write V1 RainbowFish to file.
   */
  public static void writeV1(RainbowFish rainbowFish, String filename) throws IOException {
    var map = Map.of(
        "name", rainbowFish.getName(),
        "age", String.format("%d", rainbowFish.getAge()),
        LENGTH_METERS, String.format("%d", rainbowFish.getLengthMeters()),
        WEIGHT_TONS, String.format("%d", rainbowFish.getWeightTons())
    );

    try (var fileOut = new FileOutputStream(filename);
         var objOut = new ObjectOutputStream(fileOut)) {
      objOut.writeObject(map);
    }
  }

  /**
   * Write V2 RainbowFish to file.
   */
  public static void writeV2(RainbowFishV2 rainbowFish, String filename) throws IOException {
    var map = Map.of(
        "name", rainbowFish.getName(),
        "age", String.format("%d", rainbowFish.getAge()),
        LENGTH_METERS, String.format("%d", rainbowFish.getLengthMeters()),
        WEIGHT_TONS, String.format("%d", rainbowFish.getWeightTons()),
        "angry", Boolean.toString(rainbowFish.isAngry()),
        "hungry", Boolean.toString(rainbowFish.isHungry()),
        "sleeping", Boolean.toString(rainbowFish.isSleeping())
    );

    try (var fileOut = new FileOutputStream(filename);
         var objOut = new ObjectOutputStream(fileOut)) {
      objOut.writeObject(map);
    }
  }

  /**
   * Read V1 RainbowFish from file.
   */
  public static RainbowFish readV1(String filename) throws IOException, ClassNotFoundException {
    Map<String, String> map;

    try (var fileIn = new FileInputStream(filename);
         var objIn = new ObjectInputStream(fileIn)) {
      map = (Map<String, String>) objIn.readObject();
    }

    return new RainbowFish(
        map.get("name"),
        Integer.parseInt(map.get("age")),
        Integer.parseInt(map.get(LENGTH_METERS)),
        Integer.parseInt(map.get(WEIGHT_TONS))
    );
  }
}
