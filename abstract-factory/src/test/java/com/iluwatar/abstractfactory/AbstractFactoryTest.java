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

package com.iluwatar.abstractfactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.abstractfactory.App.FactoryMaker;
import com.iluwatar.abstractfactory.App.FactoryMaker.KingdomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for abstract factory.
 */
public class AbstractFactoryTest {

  private App app = new App();
  private KingdomFactory elfFactory;
  private KingdomFactory orcFactory;

  @BeforeEach
  public void setUp() {
    elfFactory = FactoryMaker.makeFactory(KingdomType.ELF);
    orcFactory = FactoryMaker.makeFactory(KingdomType.ORC);
  }

  @Test
  public void king() {
    final var elfKing = app.getKing(elfFactory);
    assertTrue(elfKing instanceof ElfKing);
    assertEquals(ElfKing.DESCRIPTION, elfKing.getDescription());
    final var orcKing = app.getKing(orcFactory);
    assertTrue(orcKing instanceof OrcKing);
    assertEquals(OrcKing.DESCRIPTION, orcKing.getDescription());
  }

  @Test
  public void castle() {
    final var elfCastle = app.getCastle(elfFactory);
    assertTrue(elfCastle instanceof ElfCastle);
    assertEquals(ElfCastle.DESCRIPTION, elfCastle.getDescription());
    final var orcCastle = app.getCastle(orcFactory);
    assertTrue(orcCastle instanceof OrcCastle);
    assertEquals(OrcCastle.DESCRIPTION, orcCastle.getDescription());
  }

  @Test
  public void army() {
    final var elfArmy = app.getArmy(elfFactory);
    assertTrue(elfArmy instanceof ElfArmy);
    assertEquals(ElfArmy.DESCRIPTION, elfArmy.getDescription());
    final var orcArmy = app.getArmy(orcFactory);
    assertTrue(orcArmy instanceof OrcArmy);
    assertEquals(OrcArmy.DESCRIPTION, orcArmy.getDescription());
  }

  @Test
  public void createElfKingdom() {
    app.createKingdom(elfFactory);
    final var king = app.getKing();
    final var castle = app.getCastle();
    final var army = app.getArmy();
    assertTrue(king instanceof ElfKing);
    assertEquals(ElfKing.DESCRIPTION, king.getDescription());
    assertTrue(castle instanceof ElfCastle);
    assertEquals(ElfCastle.DESCRIPTION, castle.getDescription());
    assertTrue(army instanceof ElfArmy);
    assertEquals(ElfArmy.DESCRIPTION, army.getDescription());
  }

  @Test
  public void createOrcKingdom() {
    app.createKingdom(orcFactory);
    final var king = app.getKing();
    final var castle = app.getCastle();
    final var army = app.getArmy();
    assertTrue(king instanceof OrcKing);
    assertEquals(OrcKing.DESCRIPTION, king.getDescription());
    assertTrue(castle instanceof OrcCastle);
    assertEquals(OrcCastle.DESCRIPTION, castle.getDescription());
    assertTrue(army instanceof OrcArmy);
    assertEquals(OrcArmy.DESCRIPTION, army.getDescription());
  }
}
