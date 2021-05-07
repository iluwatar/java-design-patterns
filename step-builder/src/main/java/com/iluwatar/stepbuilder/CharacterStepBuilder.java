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

import java.util.ArrayList;
import java.util.List;

/**
 * The Step Builder class.
 */
public final class CharacterStepBuilder {

  private CharacterStepBuilder() {
  }

  public static NameStep newBuilder() {
    return new CharacterSteps();
  }

  /**
   * First Builder Step in charge of the Character name. Next Step available : ClassStep
   */
  public interface NameStep {
    ClassStep name(String name);
  }

  /**
   * This step is in charge of setting the Character class (fighter or wizard). Fighter choice :
   * Next Step available : WeaponStep Wizard choice : Next Step available : SpellStep
   */
  public interface ClassStep {
    WeaponStep fighterClass(String fighterClass);

    SpellStep wizardClass(String wizardClass);
  }

  /**
   * This step is in charge of the weapon. Weapon choice : Next Step available : AbilityStep No
   * weapon choice : Next Step available : BuildStep
   */
  public interface WeaponStep {
    AbilityStep withWeapon(String weapon);

    BuildStep noWeapon();
  }

  /**
   * This step is in charge of the spell. Spell choice : Next Step available : AbilityStep No spell
   * choice : Next Step available : BuildStep
   */
  public interface SpellStep {
    AbilityStep withSpell(String spell);

    BuildStep noSpell();
  }

  /**
   * This step is in charge of abilities. Next Step available : BuildStep
   */
  public interface AbilityStep {
    AbilityStep withAbility(String ability);

    BuildStep noMoreAbilities();

    BuildStep noAbilities();
  }

  /**
   * This is the final step in charge of building the Character Object. Validation should be here.
   */
  public interface BuildStep {
    Character build();
  }


  /**
   * Step Builder implementation.
   */
  private static class CharacterSteps implements NameStep, ClassStep, WeaponStep, SpellStep,
      AbilityStep, BuildStep {

    private String name;
    private String fighterClass;
    private String wizardClass;
    private String weapon;
    private String spell;
    private final List<String> abilities = new ArrayList<>();

    @Override
    public ClassStep name(String name) {
      this.name = name;
      return this;
    }

    @Override
    public WeaponStep fighterClass(String fighterClass) {
      this.fighterClass = fighterClass;
      return this;
    }

    @Override
    public SpellStep wizardClass(String wizardClass) {
      this.wizardClass = wizardClass;
      return this;
    }

    @Override
    public AbilityStep withWeapon(String weapon) {
      this.weapon = weapon;
      return this;
    }

    @Override
    public BuildStep noWeapon() {
      return this;
    }

    @Override
    public AbilityStep withSpell(String spell) {
      this.spell = spell;
      return this;
    }

    @Override
    public BuildStep noSpell() {
      return this;
    }

    @Override
    public AbilityStep withAbility(String ability) {
      this.abilities.add(ability);
      return this;
    }

    @Override
    public BuildStep noMoreAbilities() {
      return this;
    }

    @Override
    public BuildStep noAbilities() {
      return this;
    }

    @Override
    public Character build() {
      var character = new Character(name);

      if (fighterClass != null) {
        character.setFighterClass(fighterClass);
      } else {
        character.setWizardClass(wizardClass);
      }

      if (weapon != null) {
        character.setWeapon(weapon);
      } else {
        character.setSpell(spell);
      }

      if (!abilities.isEmpty()) {
        character.setAbilities(abilities);
      }

      return character;
    }
  }
}
