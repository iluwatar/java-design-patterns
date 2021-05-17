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

package com.iluwatar.specification.app;

import com.iluwatar.specification.creature.Creature;
import com.iluwatar.specification.creature.Dragon;
import com.iluwatar.specification.creature.Goblin;
import com.iluwatar.specification.creature.KillerBee;
import com.iluwatar.specification.creature.Octopus;
import com.iluwatar.specification.creature.Shark;
import com.iluwatar.specification.creature.Troll;
import com.iluwatar.specification.property.Color;
import com.iluwatar.specification.property.Movement;
import com.iluwatar.specification.selector.ColorSelector;
import com.iluwatar.specification.selector.MassEqualSelector;
import com.iluwatar.specification.selector.MassGreaterThanSelector;
import com.iluwatar.specification.selector.MassSmallerThanOrEqSelector;
import com.iluwatar.specification.selector.MovementSelector;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>The central idea of the Specification pattern is to separate the statement of how to match a
 * candidate, from the candidate object that it is matched against. As well as its usefulness in
 * selection, it is also valuable for validation and for building to order.</p>
 *
 * <p>In this example we have a pool of creatures with different properties. We then have defined
 * separate selection rules (Specifications) that we apply to the collection and as output receive
 * only the creatures that match the selection criteria.</p>
 *
 * <p>http://martinfowler.com/apsupp/spec.pdf</p>
 */
@Slf4j
public class App {

  /**
   * Program entry point.
   */
  public static void main(String[] args) {
    // initialize creatures list
    var creatures = List.of(
        new Goblin(),
        new Octopus(),
        new Dragon(),
        new Shark(),
        new Troll(),
        new KillerBee()
    );
    // so-called "hard-coded" specification
    LOGGER.info("Demonstrating hard-coded specification :");
    // find all walking creatures
    LOGGER.info("Find all walking creatures");
    print(creatures, new MovementSelector(Movement.WALKING));
    // find all dark creatures
    LOGGER.info("Find all dark creatures");
    print(creatures, new ColorSelector(Color.DARK));
    LOGGER.info("\n");
    // so-called "parameterized" specification
    LOGGER.info("Demonstrating parameterized specification :");
    // find all creatures heavier than 500kg
    LOGGER.info("Find all creatures heavier than 600kg");
    print(creatures, new MassGreaterThanSelector(600.0));
    // find all creatures heavier than 500kg
    LOGGER.info("Find all creatures lighter than or weighing exactly 500kg");
    print(creatures, new MassSmallerThanOrEqSelector(500.0));
    LOGGER.info("\n");
    // so-called "composite" specification
    LOGGER.info("Demonstrating composite specification :");
    // find all red and flying creatures
    LOGGER.info("Find all red and flying creatures");
    var redAndFlying = new ColorSelector(Color.RED).and(new MovementSelector(Movement.FLYING));
    print(creatures, redAndFlying);
    // find all creatures dark or red, non-swimming, and heavier than or equal to 400kg
    LOGGER.info("Find all scary creatures");
    var scaryCreaturesSelector = new ColorSelector(Color.DARK)
        .or(new ColorSelector(Color.RED)).and(new MovementSelector(Movement.SWIMMING).not())
        .and(new MassGreaterThanSelector(400.0).or(new MassEqualSelector(400.0)));
    print(creatures, scaryCreaturesSelector);
  }

  private static void print(List<? extends Creature> creatures, Predicate<Creature> selector) {
    creatures.stream().filter(selector).map(Objects::toString).forEach(LOGGER::info);
  }
}
