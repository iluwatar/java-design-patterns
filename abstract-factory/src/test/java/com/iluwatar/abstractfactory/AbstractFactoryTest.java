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

package com.iluwatar.abstractfactory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for abstract factory.
 */
class AbstractFactoryTest {

  private final App app = new App();

  @Test
  void verifyKingCreation() {
    app.createKingdom(Kingdom.FactoryMaker.KingdomType.ELF);
    final var kingdom = app.getKingdom();

    final var elfKing = kingdom.getKing();
    assertTrue(elfKing instanceof ElfKing);
    assertEquals(ElfKing.DESCRIPTION, elfKing.getDescription());

    app.createKingdom(Kingdom.FactoryMaker.KingdomType.ORC);
    final var orcKing = kingdom.getKing();
    assertTrue(orcKing instanceof OrcKing);
    assertEquals(OrcKing.DESCRIPTION, orcKing.getDescription());
  }

  @Test
  void verifyCastleCreation() {
    app.createKingdom(Kingdom.FactoryMaker.KingdomType.ELF);
    final var kingdom = app.getKingdom();

    final var elfCastle = kingdom.getCastle();
    assertTrue(elfCastle instanceof ElfCastle);
    assertEquals(ElfCastle.DESCRIPTION, elfCastle.getDescription());

    app.createKingdom(Kingdom.FactoryMaker.KingdomType.ORC);
    final var orcCastle = kingdom.getCastle();
    assertTrue(orcCastle instanceof OrcCastle);
    assertEquals(OrcCastle.DESCRIPTION, orcCastle.getDescription());
  }

  @Test
  void verifyArmyCreation() {
    app.createKingdom(Kingdom.FactoryMaker.KingdomType.ELF);
    final var kingdom = app.getKingdom();

    final var elfArmy = kingdom.getArmy();
    assertTrue(elfArmy instanceof ElfArmy);
    assertEquals(ElfArmy.DESCRIPTION, elfArmy.getDescription());

    app.createKingdom(Kingdom.FactoryMaker.KingdomType.ORC);
    final var orcArmy = kingdom.getArmy();
    assertTrue(orcArmy instanceof OrcArmy);
    assertEquals(OrcArmy.DESCRIPTION, orcArmy.getDescription());
  }

  @Test
  void verifyElfKingdomCreation() {
    app.createKingdom(Kingdom.FactoryMaker.KingdomType.ELF);
    final var kingdom = app.getKingdom();

    final var king = kingdom.getKing();
    final var castle = kingdom.getCastle();
    final var army = kingdom.getArmy();
    assertTrue(king instanceof ElfKing);
    assertEquals(ElfKing.DESCRIPTION, king.getDescription());
    assertTrue(castle instanceof ElfCastle);
    assertEquals(ElfCastle.DESCRIPTION, castle.getDescription());
    assertTrue(army instanceof ElfArmy);
    assertEquals(ElfArmy.DESCRIPTION, army.getDescription());
  }

  @Test
  void verifyOrcKingdomCreation() {
    app.createKingdom(Kingdom.FactoryMaker.KingdomType.ORC);
    final var kingdom = app.getKingdom();

    final var king = kingdom.getKing();
    final var castle = kingdom.getCastle();
    final var army = kingdom.getArmy();
    assertTrue(king instanceof OrcKing);
    assertEquals(OrcKing.DESCRIPTION, king.getDescription());
    assertTrue(castle instanceof OrcCastle);
    assertEquals(OrcCastle.DESCRIPTION, castle.getDescription());
    assertTrue(army instanceof OrcArmy);
    assertEquals(OrcArmy.DESCRIPTION, army.getDescription());
  }
}
