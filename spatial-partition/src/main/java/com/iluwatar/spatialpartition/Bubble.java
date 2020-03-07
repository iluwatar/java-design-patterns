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

package com.iluwatar.spatialpartition;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Bubble class extends Point. In this example, we create several bubbles in the field, let them
 * move and keep track of which ones have popped and which ones remain.
 */

public class Bubble extends Point<Bubble> {
  private static final Logger LOGGER = LoggerFactory.getLogger(Bubble.class);
  private static final Random RANDOM = new Random();

  final int radius;

  Bubble(int x, int y, int id, int radius) {
    super(x, y, id);
    this.radius = radius;
  }

  void move() {
    //moves by 1 unit in either direction
    this.coordinateX += RANDOM.nextInt(3) - 1;
    this.coordinateY += RANDOM.nextInt(3) - 1;
  }

  boolean touches(Bubble b) {
    //distance between them is greater than sum of radii (both sides of equation squared)
    return (this.coordinateX - b.coordinateX) * (this.coordinateX - b.coordinateX)
        + (this.coordinateY - b.coordinateY) * (this.coordinateY - b.coordinateY)
        <= (this.radius + b.radius) * (this.radius + b.radius);
  }

  void pop(Hashtable<Integer, Bubble> allBubbles) {
    LOGGER.info("Bubble " + this.id
        + " popped at (" + this.coordinateX + "," + this.coordinateY + ")!");
    allBubbles.remove(this.id);
  }

  void handleCollision(Collection<? extends Point> toCheck, Hashtable<Integer, Bubble> allBubbles) {
    var toBePopped = false; //if any other bubble collides with it, made true
    for (var point : toCheck) {
      var otherId = point.id;
      if (allBubbles.get(otherId) != null && //the bubble hasn't been popped yet
          this.id != otherId && //the two bubbles are not the same
          this.touches(allBubbles.get(otherId))) { //the bubbles touch
        allBubbles.get(otherId).pop(allBubbles);
        toBePopped = true;
      }
    }
    if (toBePopped) {
      this.pop(allBubbles);
    }
  }
}
