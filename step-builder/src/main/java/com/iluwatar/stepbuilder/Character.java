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

package com.iluwatar.stepbuilder;

import java.util.List;

/**
 * The class with many parameters.
 */
public class Character {

  private String name;
  private String fighterClass;
  private String wizardClass;
  private String weapon;
  private String spell;
  private List<String> abilities;

  public Character(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFighterClass() {
    return fighterClass;
  }

  public void setFighterClass(String fighterClass) {
    this.fighterClass = fighterClass;
  }

  public String getWizardClass() {
    return wizardClass;
  }

  public void setWizardClass(String wizardClass) {
    this.wizardClass = wizardClass;
  }

  public String getWeapon() {
    return weapon;
  }

  public void setWeapon(String weapon) {
    this.weapon = weapon;
  }

  public String getSpell() {
    return spell;
  }

  public void setSpell(String spell) {
    this.spell = spell;
  }

  public List<String> getAbilities() {
    return abilities;
  }

  public void setAbilities(List<String> abilities) {
    this.abilities = abilities;
  }

  @Override
  public String toString() {
    return new StringBuilder()
        .append("This is a ")
        .append(fighterClass != null ? fighterClass : wizardClass)
        .append(" named ")
        .append(name)
        .append(" armed with a ")
        .append(weapon != null ? weapon : spell != null ? spell : "with nothing")
        .append(abilities != null ? " and wielding " + abilities + " abilities" : "")
        .append('.')
        .toString();
  }
}
