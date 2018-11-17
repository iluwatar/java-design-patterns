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

/**
 * 
 * HeroOtherForm, the class with many parameters.
 * 
 */
public final class HeroOtherForm {

  private Profession profession;
  private String name;
  private HairType hairType;
  private HairColor hairColor;
  private Armor armor;
  private Weapon weapon;

  public Profession getProfession() {
    return profession;
  }

  public void setProfession(Profession profession) {
    this.profession = profession;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public HairType getHairType() {
    return hairType;
  }

  public void setHairType(HairType hairType) {
    this.hairType = hairType;
  }

  public HairColor getHairColor() {
    return hairColor;
  }

  public void setHairColor(HairColor hairColor) {
    this.hairColor = hairColor;
  }

  public Armor getArmor() {
    return armor;
  }

  public void setArmor(Armor armor) {
    this.armor = armor;
  }

  public Weapon getWeapon() {
    return weapon;
  }

  public void setWeapon(Weapon weapon) {
    this.weapon = weapon;
  }

  @Override
  public String toString() {

    StringBuilder sb = new StringBuilder();
    sb.append("This is a ").append(profession).append(" named ").append(name);

    if (hairColor != null || hairType != null) {
      sb.append(" with ");
      if (hairColor != null) {
        sb.append(hairColor).append(' ');
      }
      if (hairType != null) {
        sb.append(hairType).append(' ');
      }
      sb.append(hairType != HairType.BALD ? "hair" : "head");
    }
    if (armor != null) {
      sb.append(" wearing ").append(armor);
    }
    if (weapon != null) {
      sb.append(" and wielding a ").append(weapon);
    }
    sb.append('.');
    return sb.toString();
  }

  /**
   * 
   * The builder class method.
   * 
   */
  public static HeroOtherForm build(final Profession profession, final String name,
      final HairType hairType, final HairColor hairColor, final Armor armor, final Weapon weapon) {

    HeroOtherForm result = new HeroOtherForm();

    if (profession == null || name == null) {
      result = null;
      throw new IllegalArgumentException("profession and name can not be null");
    }
    result.setProfession(profession);
    result.setName(name);
    result.setHairType(hairType);
    result.setHairColor(hairColor);
    result.setArmor(armor);
    result.setWeapon(weapon);

    return result;
  }

}