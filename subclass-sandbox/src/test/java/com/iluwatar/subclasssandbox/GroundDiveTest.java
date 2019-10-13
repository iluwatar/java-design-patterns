/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.subclasssandbox;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

/**
 * GroundDive unit tests.
 */
public class GroundDiveTest {

  @Rule
  public SystemOutRule log = new SystemOutRule().enableLog();

  @Test
  public void testMove() {
    log.clearLog();
    GroundDive groundDive = new GroundDive();
    groundDive.move(1.0, 1.0, 1.0);
    String outputLog = log.getLog().split("-")[1].trim();
    String expectedLog = "Move to ( 1.0, 1.0, 1.0 )";
    Assert.assertEquals(outputLog, expectedLog);
  }

  @Test
  public void testPlaySound() {
    log.clearLog();
    GroundDive groundDive = new GroundDive();
    groundDive.playSound("SOUND_NAME", 1);
    String outputLog = log.getLog().split("-")[1].trim();
    String expectedLog = "Play SOUND_NAME with volumn 1";
    Assert.assertEquals(outputLog, expectedLog);
  }

  @Test
  public void testSpawnParticles() {
    log.clearLog();
    GroundDive groundDive = new GroundDive();
    groundDive.spawnParticles("PARTICLE_TYPE", 100);
    String outputLog = log.getLog().split("-")[1].trim();
    String expectedLog = "Spawn 100 particle with type PARTICLE_TYPE";
    Assert.assertEquals(outputLog, expectedLog);
  }

  @Test
  public void testActivate() {
    log.clearLog();
    GroundDive groundDive = new GroundDive();
    groundDive.activate();
    System.out.println(log.getLog());
  }

}
