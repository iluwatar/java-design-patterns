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
package crtp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class FightTest {

  /**
   * A fighter has signed a contract with a promotion, and he will face some other fighters. A list of opponents is ready
   * but for some reason not all of them belong to the same weight class. Let's ensure that the fighter will only face
   * opponents in the same weight class.
   */
  @Test
  void testFighterCanFightOnlyAgainstSameWeightOpponents() {
    MmaBantamweightFighter fighter = new MmaBantamweightFighter("Joe", "Johnson", "The Geek", "Muay Thai");
    List<MmaFighter<?>> opponents = getOpponents();
    List<MmaFighter<?>> challenged = new ArrayList<>();

    opponents.forEach(challenger -> {
      try {
        ((MmaBantamweightFighter) challenger).fight(fighter);
        challenged.add(challenger);
      } catch (ClassCastException e) {
          LOGGER.error(e.getMessage());
      }
    });

    assertFalse(challenged.isEmpty());
    assertTrue(challenged.stream().allMatch(c -> c instanceof MmaBantamweightFighter));
  }

  private static List<MmaFighter<?>> getOpponents() {
    return List.of(
          new MmaBantamweightFighter("Ed", "Edwards", "The Problem Solver", "Judo"),
          new MmaLightweightFighter("Evan", "Evans", "Clean Coder", "Sambo"),
          new MmaHeavyweightFighter("Dave", "Davidson", "The Bug Smasher", "Kickboxing"),
          new MmaBantamweightFighter("Ray", "Raymond", "Scrum Master", "Karate"),
          new MmaHeavyweightFighter("Jack", "Jackson", "The Pragmatic", "Brazilian Jiu-Jitsu")
    );
  }


}
