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
package com.iluwatar.factorykit

// ABOUTME: Functional interface and companion factory for creating Weapon instances by type.
// ABOUTME: Uses a map of suppliers configured via a Builder lambda to produce weapons on demand.

/**
 * Functional interface, an example of the factory-kit design pattern.
 *
 * Instance created locally gives an opportunity to strictly define which object types the instance
 * of a factory will be able to create.
 *
 * Factory is a placeholder for [Builder]s with [WeaponFactory.create] method to initialize
 * new objects.
 */
fun interface WeaponFactory {

    /**
     * Creates an instance of the given type.
     *
     * @param name representing enum of an object type to be created.
     * @return new instance of a requested class implementing [Weapon] interface.
     */
    fun create(name: WeaponType): Weapon

    companion object {
        /**
         * Creates factory - placeholder for specified [Builder]s.
         *
         * @param consumer configuration block for the new builder to the factory.
         * @return factory with specified [Builder]s
         */
        fun factory(consumer: (Builder) -> Unit): WeaponFactory {
            val map = mutableMapOf<WeaponType, () -> Weapon>()
            consumer(Builder { name, supplier -> map[name] = supplier })
            return WeaponFactory { name -> map.getValue(name).invoke() }
        }
    }
}
