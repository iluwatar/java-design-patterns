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

package com.iluwatar.identityfield;

import lombok.var;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * The type Pearson test.
 */
public class PersonTest {
    /**
     * Pearson test.
     */
    @Test
    public void SetNameTest() {
        var person = new Person(1, "ramesh", 28, "male");
        assertEquals(1, person.getId());
        assertEquals("ramesh", person.getName());
        assertEquals(28, person.getAge());
        assertEquals("male", person.getGender());
        var name = "prakash";
        person.setName(name);
        assertEquals("prakash", person.getName());
        assertEquals(1, person.getId());
    }

    @Test
    public void SetAgeTest() {
        var person = new Person(1, "ramesh", 28, "male");
        assertEquals(1, person.getId());
        assertEquals("ramesh", person.getName());
        assertEquals(28, person.getAge());
        assertEquals("male", person.getGender());
        var age = 29;
        person.setAge(age);
        assertEquals(29, person.getAge());
        assertEquals(1, person.getId());
    }

    @Test
    public void SetGenderTest() {
        var person = new Person(1, "ramesh", 28, "male");
        assertEquals(1, person.getId());
        assertEquals("ramesh", person.getName());
        assertEquals(28, person.getAge());
        assertEquals("male", person.getGender());
        var gender = "female";
        person.setGender(gender);
        assertEquals("female", person.getGender());
        assertEquals(1, person.getId());
    }

    @Test
    public void SetIdTest() {
        var person = new Person(1, "ramesh", 28, "male");
        assertEquals(1, person.getId());
        assertEquals("ramesh", person.getName());
        assertEquals(28, person.getAge());
        assertEquals("male", person.getGender());
        var id = 10;
        person.setId(id);
        assertEquals("ramesh", person.getName());
        assertEquals(10, person.getId());
    }
}