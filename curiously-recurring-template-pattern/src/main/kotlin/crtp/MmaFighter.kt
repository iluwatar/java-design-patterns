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
package crtp

import io.github.oshai.kotlinlogging.KotlinLogging

// ABOUTME: Base MMA fighter class implementing the Curiously Recurring Template Pattern.
// ABOUTME: Uses self-referential generics to ensure fighters only fight same weight class opponents.

private val logger = KotlinLogging.logger {}

/**
 * MmaFighter class.
 *
 * @param T MmaFighter derived class that uses itself as type parameter.
 */
open class MmaFighter<T : MmaFighter<T>>(
    val name: String,
    val surname: String,
    val nickName: String,
    val speciality: String
) : Fighter<T> {

    override fun fight(opponent: T) {
        logger.info { "$this is going to fight against $opponent" }
    }

    override fun toString(): String {
        return "MmaFighter(name=$name, surname=$surname, nickName=$nickName, speciality=$speciality)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MmaFighter<*>) return false
        return name == other.name &&
            surname == other.surname &&
            nickName == other.nickName &&
            speciality == other.speciality
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + surname.hashCode()
        result = 31 * result + nickName.hashCode()
        result = 31 * result + speciality.hashCode()
        return result
    }
}
