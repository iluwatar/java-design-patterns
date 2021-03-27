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

package com.iluwatar.subclasssandbox;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOutNormalized;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.stefanbirkner.systemlambda.Statement;
import org.junit.jupiter.api.Test;

/**
 * SkyLaunch unit tests.
 */
class SkyLaunchTest {

  @Test
  void testMove() throws Exception {
    var skyLaunch = new SkyLaunch();
    var outputLog = getLogContent(() -> skyLaunch.move(1.0, 1.0, 1.0));
    var expectedLog = "Move to ( 1.0, 1.0, 1.0 )";
    assertEquals(outputLog, expectedLog);
  }

  @Test
  void testPlaySound() throws Exception {
    var skyLaunch = new SkyLaunch();
    var outputLog = getLogContent(() -> skyLaunch.playSound("SOUND_NAME", 1));
    var expectedLog = "Play SOUND_NAME with volumn 1";
    assertEquals(outputLog, expectedLog);
  }

  @Test
  void testSpawnParticles() throws Exception {
    var skyLaunch = new SkyLaunch();
    var outputLog = getLogContent(
        () -> skyLaunch.spawnParticles("PARTICLE_TYPE", 100));
    var expectedLog = "Spawn 100 particle with type PARTICLE_TYPE";
    assertEquals(outputLog, expectedLog);
  }

  @Test
  void testActivate() throws Exception {
    var skyLaunch = new SkyLaunch();
    var logs = tapSystemOutNormalized(skyLaunch::activate)
        .split("\n");
    final var expectedSize = 3;
    final var log1 = getLogContent(logs[0]);
    final var expectedLog1 = "Move to ( 0.0, 0.0, 20.0 )";
    final var log2 = getLogContent(logs[1]);
    final var expectedLog2 = "Play SKYLAUNCH_SOUND with volumn 1";
    final var log3 = getLogContent(logs[2]);
    final var expectedLog3 = "Spawn 100 particle with type SKYLAUNCH_PARTICLE";
    assertEquals(logs.length, expectedSize);
    assertEquals(log1, expectedLog1);
    assertEquals(log2, expectedLog2);
    assertEquals(log3, expectedLog3);
  }

  private String getLogContent(Statement statement) throws Exception {
    var log = tapSystemOutNormalized(statement);
    return getLogContent(log);
  }

  private String getLogContent(String log) {
    return log.split("-")[1].trim();
  }
}
