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
package com.iluwatar.stepbuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** CharacterStepBuilderTest */
class CharacterStepBuilderTest {

  /** Build a new wizard {@link Character} and verify if it has the expected attributes */
  @Test
  void testBuildWizard() {
    final var character =
        CharacterStepBuilder.newBuilder()
            .name("Merlin")
            .wizardClass("alchemist")
            .withSpell("poison")
            .withAbility("invisibility")
            .withAbility("wisdom")
            .noMoreAbilities()
            .build();

    assertEquals("Merlin", character.getName());
    assertEquals("alchemist", character.getWizardClass());
    assertEquals("poison", character.getSpell());
    assertNotNull(character.toString());

    final var abilities = character.getAbilities();
    assertNotNull(abilities);
    assertEquals(2, abilities.size());
    assertTrue(abilities.contains("invisibility"));
    assertTrue(abilities.contains("wisdom"));
  }

  /**
   * Build a new wizard {@link Character} without spell or abilities and verify if it has the
   * expected attributes
   */
  @Test
  void testBuildPoorWizard() {
    final var character =
        CharacterStepBuilder.newBuilder().name("Merlin").wizardClass("alchemist").noSpell().build();

    assertEquals("Merlin", character.getName());
    assertEquals("alchemist", character.getWizardClass());
    assertNull(character.getSpell());
    assertNull(character.getAbilities());
    assertNotNull(character.toString());
  }

  /** Build a new wizard {@link Character} and verify if it has the expected attributes */
  @Test
  void testBuildWeakWizard() {
    final var character =
        CharacterStepBuilder.newBuilder()
            .name("Merlin")
            .wizardClass("alchemist")
            .withSpell("poison")
            .noAbilities()
            .build();

    assertEquals("Merlin", character.getName());
    assertEquals("alchemist", character.getWizardClass());
    assertEquals("poison", character.getSpell());
    assertNull(character.getAbilities());
    assertNotNull(character.toString());
  }

  /** Build a new warrior {@link Character} and verify if it has the expected attributes */
  @Test
  void testBuildWarrior() {
    final var character =
        CharacterStepBuilder.newBuilder()
            .name("Cuauhtemoc")
            .fighterClass("aztec")
            .withWeapon("spear")
            .withAbility("speed")
            .withAbility("strength")
            .noMoreAbilities()
            .build();

    assertEquals("Cuauhtemoc", character.getName());
    assertEquals("aztec", character.getFighterClass());
    assertEquals("spear", character.getWeapon());
    assertNotNull(character.toString());

    final var abilities = character.getAbilities();
    assertNotNull(abilities);
    assertEquals(2, abilities.size());
    assertTrue(abilities.contains("speed"));
    assertTrue(abilities.contains("strength"));
  }

  /**
   * Build a new wizard {@link Character} without weapon and abilities and verify if it has the
   * expected attributes
   */
  @Test
  void testBuildPoorWarrior() {
    final var character =
        CharacterStepBuilder.newBuilder()
            .name("Poor warrior")
            .fighterClass("none")
            .noWeapon()
            .build();

    assertEquals("Poor warrior", character.getName());
    assertEquals("none", character.getFighterClass());
    assertNull(character.getWeapon());
    assertNull(character.getAbilities());
    assertNotNull(character.toString());
  }

  /**
   * Build a new warrior {@link Character} without any abilities, but with a weapon and verify if it
   * has the expected attributes
   */
  @Test
  void testBuildWeakWarrior() {
    final var character =
        CharacterStepBuilder.newBuilder()
            .name("Weak warrior")
            .fighterClass("none")
            .withWeapon("Slingshot")
            .noAbilities()
            .build();

    assertEquals("Weak warrior", character.getName());
    assertEquals("none", character.getFighterClass());
    assertEquals("Slingshot", character.getWeapon());
    assertNull(character.getAbilities());
    assertNotNull(character.toString());
  }
}
