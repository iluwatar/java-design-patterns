/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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
package com.iluwatar.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.iluwatar.builder.Armor;
import com.iluwatar.builder.HairColor;
import com.iluwatar.builder.HairType;
import com.iluwatar.builder.Hero;
import com.iluwatar.builder.Profession;
import com.iluwatar.builder.Warrior;
import com.iluwatar.builder.Weapon;

/**
 * WarriorTest, the class with many parameters.
 * Date: 18/11/2018 - 23:00 PM
 *
 * @author Jeroen Meulemeester
 * @author Evaldo Junior
 */
public class WarriorTest {

  /**
   * Test if we get the expected exception when trying to create a hero without a profession
   */
  @Test
  public void testMissingProfession() throws Exception {
    assertThrows(IllegalArgumentException.class, () -> new Hero.Builder(null, "Sir without a job"));
  }

  /**
   * Test if we get the expected exception when trying to create a hero without a name
   */
  @Test
  public void testMissingName() throws Exception {
    assertThrows(IllegalArgumentException.class, () -> new Hero.Builder(Profession.THIEF, null));
  }
  
  /**
   * Test if the hero build by the other builder form of the design pattern
   * Builder has the correct attributes, as requested
   */
  @Test
  public void testBuildWarrior() throws Exception {
    final String heroName = "Sir Lancelot";
    
    final Warrior warrior = Warrior.build(Profession.WARRIOR, 
        heroName,
        HairType.LONG_CURLY,
        HairColor.BLOND,
        Armor.CHAIN_MAIL,
        Weapon.SWORD);

    assertNotNull(warrior);
    assertNotNull(warrior.toString());
    assertEquals(Profession.WARRIOR, warrior.getProfession());
    assertEquals(heroName, warrior.getName());
    assertEquals(Armor.CHAIN_MAIL, warrior.getArmor());
    assertEquals(Weapon.SWORD, warrior.getWeapon());
    assertEquals(HairType.LONG_CURLY, warrior.getHairType());
    assertEquals(HairColor.BLOND, warrior.getHairColor());

  }

}