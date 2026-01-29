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
package com.iluwatar.property

// ABOUTME: Represents a game character with stat properties inherited through a prototype chain.
// ABOUTME: Implements the Property pattern where objects delegate to parent prototypes for missing stats.

/** Represents a Character in the game and their abilities (base stats). */
class Character : Prototype {

    /** Enumeration of Character types. */
    enum class Type {
        WARRIOR,
        MAGE,
        ROGUE
    }

    private val prototype: Prototype
    private val properties: MutableMap<Stats, Int?> = mutableMapOf()

    val name: String?
    val type: Type?

    /** Creates a root character with a null-object prototype. */
    constructor() {
        this.name = null
        this.type = null
        this.prototype = object : Prototype {
            // Null-value object
            override fun get(stat: Stats): Int? = null
            override fun has(stat: Stats): Boolean = false
            override fun set(stat: Stats, value: Int?) { /* Does Nothing */ }
            override fun remove(stat: Stats) { /* Does Nothing */ }
        }
    }

    constructor(type: Type, prototype: Prototype) {
        this.name = null
        this.type = type
        this.prototype = prototype
    }

    constructor(name: String, prototype: Character) {
        this.name = name
        this.type = prototype.type
        this.prototype = prototype
    }

    override fun get(stat: Stats): Int? =
        if (properties.containsKey(stat)) {
            properties[stat]
        } else {
            prototype.get(stat)
        }

    override fun has(stat: Stats): Boolean = get(stat) != null

    override fun set(stat: Stats, value: Int?) {
        properties[stat] = value
    }

    override fun remove(stat: Stats) {
        properties[stat] = null
    }

    override fun toString(): String = buildString {
        if (name != null) {
            append("Player: $name\n")
        }
        if (type != null) {
            append("Character type: ${type.name}\n")
        }
        append("Stats:\n")
        for (stat in Stats.entries) {
            val value = get(stat) ?: continue
            append(" - ${stat.name}:$value\n")
        }
    }
}
