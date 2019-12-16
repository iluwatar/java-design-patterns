/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tolerant Reader is an integration pattern that helps creating robust communication systems. The
 * idea is to be as tolerant as possible when reading data from another service. This way, when the
 * communication schema changes, the readers must not break.
 *
 * <p>In this example we use Java serialization to write representations of {@link RainbowFish}
 * objects to file. {@link RainbowFish} is the initial version which we can easily read and write
 * using {@link RainbowFishSerializer} methods. {@link RainbowFish} then evolves to {@link
 * RainbowFishV2} and we again write it to file with a method designed to do just that. However, the
 * reader client does not know about the new format and still reads with the method designed for V1
 * schema. Fortunately the reading method has been designed with the Tolerant Reader pattern and
 * does not break even though {@link RainbowFishV2} has new fields that are serialized.
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Program entry point.
   */
  public static void main(String[] args) throws IOException, ClassNotFoundException {
    // Write V1
    var fishV1 = new RainbowFish("Zed", 10, 11, 12);
    LOGGER.info("fishV1 name={} age={} length={} weight={}", fishV1.getName(),
        fishV1.getAge(), fishV1.getLengthMeters(), fishV1.getWeightTons());
    RainbowFishSerializer.writeV1(fishV1, "fish1.out");
    // Read V1
    var deserializedRainbowFishV1 = RainbowFishSerializer.readV1("fish1.out");
    LOGGER.info("deserializedFishV1 name={} age={} length={} weight={}",
        deserializedRainbowFishV1.getName(), deserializedRainbowFishV1.getAge(),
        deserializedRainbowFishV1.getLengthMeters(), deserializedRainbowFishV1.getWeightTons());
    // Write V2
    var fishV2 = new RainbowFishV2("Scar", 5, 12, 15, true, true, true);
    LOGGER.info(
        "fishV2 name={} age={} length={} weight={} sleeping={} hungry={} angry={}",
        fishV2.getName(), fishV2.getAge(), fishV2.getLengthMeters(), fishV2.getWeightTons(),
        fishV2.getHungry(), fishV2.getAngry(), fishV2.getSleeping());
    RainbowFishSerializer.writeV2(fishV2, "fish2.out");
    // Read V2 with V1 method
    var deserializedFishV2 = RainbowFishSerializer.readV1("fish2.out");
    LOGGER.info("deserializedFishV2 name={} age={} length={} weight={}",
        deserializedFishV2.getName(), deserializedFishV2.getAge(),
        deserializedFishV2.getLengthMeters(), deserializedFishV2.getWeightTons());
  }
}
