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

package com.iluwatar.bytecode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represent game objects which properties can be changed by instructions interpreted by
 * virtual machine.
 */
public class Wizard {
  private static final Logger LOGGER = LoggerFactory.getLogger(Wizard.class);

  private int health;

  private int agility;
  private int wisdom;

  private int numberOfPlayedSounds;
  private int numberOfSpawnedParticles;

  public int getHealth() {
    return health;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  public int getAgility() {
    return agility;
  }

  public void setAgility(int agility) {
    this.agility = agility;
  }

  public int getWisdom() {
    return wisdom;
  }

  public void setWisdom(int wisdom) {
    this.wisdom = wisdom;
  }

  public void playSound() {
    LOGGER.info("Playing sound");
    numberOfPlayedSounds++;
  }

  public void spawnParticles() {
    LOGGER.info("Spawning particles");
    numberOfSpawnedParticles++;
  }

  public int getNumberOfPlayedSounds() {
    return numberOfPlayedSounds;
  }

  public int getNumberOfSpawnedParticles() {
    return numberOfSpawnedParticles;
  }
}
