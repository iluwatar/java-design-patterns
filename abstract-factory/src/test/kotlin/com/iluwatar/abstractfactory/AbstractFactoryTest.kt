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
package com.iluwatar.abstractfactory

// ABOUTME: Tests for the Abstract Factory pattern verifying correct kingdom component creation.
// ABOUTME: Validates that Elf and Orc factories produce the expected King, Castle, and Army types.

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/** Tests for abstract factory. */
class AbstractFactoryTest {
    @Test
    fun verifyKingCreation() {
        createKingdom(Kingdom.FactoryMaker.KingdomType.ELF)

        val elfKing = kingdom.king
        assertTrue(elfKing is ElfKing)
        assertEquals(ElfKing.DESCRIPTION, elfKing?.description)

        createKingdom(Kingdom.FactoryMaker.KingdomType.ORC)
        val orcKing = kingdom.king
        assertTrue(orcKing is OrcKing)
        assertEquals(OrcKing.DESCRIPTION, orcKing?.description)
    }

    @Test
    fun verifyCastleCreation() {
        createKingdom(Kingdom.FactoryMaker.KingdomType.ELF)

        val elfCastle = kingdom.castle
        assertTrue(elfCastle is ElfCastle)
        assertEquals(ElfCastle.DESCRIPTION, elfCastle?.description)

        createKingdom(Kingdom.FactoryMaker.KingdomType.ORC)
        val orcCastle = kingdom.castle
        assertTrue(orcCastle is OrcCastle)
        assertEquals(OrcCastle.DESCRIPTION, orcCastle?.description)
    }

    @Test
    fun verifyArmyCreation() {
        createKingdom(Kingdom.FactoryMaker.KingdomType.ELF)

        val elfArmy = kingdom.army
        assertTrue(elfArmy is ElfArmy)
        assertEquals(ElfArmy.DESCRIPTION, elfArmy?.description)

        createKingdom(Kingdom.FactoryMaker.KingdomType.ORC)
        val orcArmy = kingdom.army
        assertTrue(orcArmy is OrcArmy)
        assertEquals(OrcArmy.DESCRIPTION, orcArmy?.description)
    }

    @Test
    fun verifyElfKingdomCreation() {
        createKingdom(Kingdom.FactoryMaker.KingdomType.ELF)

        val king = kingdom.king
        val castle = kingdom.castle
        val army = kingdom.army
        assertTrue(king is ElfKing)
        assertEquals(ElfKing.DESCRIPTION, king?.description)
        assertTrue(castle is ElfCastle)
        assertEquals(ElfCastle.DESCRIPTION, castle?.description)
        assertTrue(army is ElfArmy)
        assertEquals(ElfArmy.DESCRIPTION, army?.description)
    }

    @Test
    fun verifyOrcKingdomCreation() {
        createKingdom(Kingdom.FactoryMaker.KingdomType.ORC)

        val king = kingdom.king
        val castle = kingdom.castle
        val army = kingdom.army
        assertTrue(king is OrcKing)
        assertEquals(OrcKing.DESCRIPTION, king?.description)
        assertTrue(castle is OrcCastle)
        assertEquals(OrcCastle.DESCRIPTION, castle?.description)
        assertTrue(army is OrcArmy)
        assertEquals(OrcArmy.DESCRIPTION, army?.description)
    }
}