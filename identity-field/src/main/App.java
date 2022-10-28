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

import java.util.LinkedList;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The identity field pattern is used to save a database ID field in an object
 * to maintain an identity between an in-memory object and a database row.
 *
 * <p>In this example, the {@link Person} is used to save each row in the database in a java object.
 * It uses the field id to identify the row in the database.
 */
@Slf4j
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    /**
     * The entry point of application.
     *
     * @param args the input arguments  、
     */

    public static void main(String[] args) {
        var pearsonList = new LinkedList<Person>();
        for (var i = 0; i < 5; i++) {
            var name = "pearson" + i;
            var person = new Person(i, name, i, "male");
            pearsonList.add(person);
        }
        for (var i = 0; i < 5; i++) {
            LOGGER.info("Id: " + pearsonList.get(i).getId()
                    + " Name: " + pearsonList.get(i).getName()
                    + " Age: " + pearsonList.get(i).getAge()
                    + " Gender: " + pearsonList.get(i).getGender());
        }
    }
}