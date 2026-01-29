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

// ABOUTME: Step Builder pattern implementation for creating Character objects.
// ABOUTME: Guides users through mandatory steps to construct valid characters with type-safe fluent API.
package com.iluwatar.stepbuilder

/**
 * The Step Builder class.
 */
object CharacterStepBuilder {

    @JvmStatic
    fun newBuilder(): NameStep = CharacterSteps()

    /** First Builder Step in charge of the Character name. Next Step available: ClassStep */
    interface NameStep {
        fun name(name: String): ClassStep
    }

    /**
     * This step is in charge of setting the Character class (fighter or wizard).
     * Fighter choice: Next Step available: WeaponStep
     * Wizard choice: Next Step available: SpellStep
     */
    interface ClassStep {
        fun fighterClass(fighterClass: String): WeaponStep
        fun wizardClass(wizardClass: String): SpellStep
    }

    /**
     * This step is in charge of the weapon.
     * Weapon choice: Next Step available: AbilityStep
     * No weapon choice: Next Step available: BuildStep
     */
    interface WeaponStep {
        fun withWeapon(weapon: String): AbilityStep
        fun noWeapon(): BuildStep
    }

    /**
     * This step is in charge of the spell.
     * Spell choice: Next Step available: AbilityStep
     * No spell choice: Next Step available: BuildStep
     */
    interface SpellStep {
        fun withSpell(spell: String): AbilityStep
        fun noSpell(): BuildStep
    }

    /** This step is in charge of abilities. Next Step available: BuildStep */
    interface AbilityStep {
        fun withAbility(ability: String): AbilityStep
        fun noMoreAbilities(): BuildStep
        fun noAbilities(): BuildStep
    }

    /**
     * This is the final step in charge of building the Character Object.
     * Validation should be here.
     */
    interface BuildStep {
        fun build(): Character
    }

    /** Step Builder implementation. */
    private class CharacterSteps : NameStep, ClassStep, WeaponStep, SpellStep, AbilityStep, BuildStep {
        private var name: String = ""
        private var fighterClass: String? = null
        private var wizardClass: String? = null
        private var weapon: String? = null
        private var spell: String? = null
        private val abilities: MutableList<String> = mutableListOf()

        override fun name(name: String): ClassStep {
            this.name = name
            return this
        }

        override fun fighterClass(fighterClass: String): WeaponStep {
            this.fighterClass = fighterClass
            return this
        }

        override fun wizardClass(wizardClass: String): SpellStep {
            this.wizardClass = wizardClass
            return this
        }

        override fun withWeapon(weapon: String): AbilityStep {
            this.weapon = weapon
            return this
        }

        override fun noWeapon(): BuildStep = this

        override fun withSpell(spell: String): AbilityStep {
            this.spell = spell
            return this
        }

        override fun noSpell(): BuildStep = this

        override fun withAbility(ability: String): AbilityStep {
            abilities.add(ability)
            return this
        }

        override fun noMoreAbilities(): BuildStep = this

        override fun noAbilities(): BuildStep = this

        override fun build(): Character {
            val character = Character(name)

            if (fighterClass != null) {
                character.fighterClass = fighterClass
            } else {
                character.wizardClass = wizardClass
            }

            if (weapon != null) {
                character.weapon = weapon
            } else {
                character.spell = spell
            }

            if (abilities.isNotEmpty()) {
                character.abilities = abilities
            }

            return character
        }
    }
}
