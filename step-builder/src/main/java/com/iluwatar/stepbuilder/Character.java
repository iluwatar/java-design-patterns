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
    StringBuilder sb = new StringBuilder();
    sb.append("This is a ")
            .append(fighterClass != null ? fighterClass : wizardClass)
            .append(" named ")
            .append(name)
            .append(" armed with a ")
            .append(weapon != null ? weapon : spell != null ? spell : "with nothing")
            .append(abilities != null ? (" and wielding " + abilities + " abilities") : "")
            .append(".");
    return sb.toString();
  }
}
