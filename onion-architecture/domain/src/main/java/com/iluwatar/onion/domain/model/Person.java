/*
 *
 *  * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *  *
 *  * The MIT License
 *  * Copyright © 2014-2022 Ilkka Seppälä
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in
 *  * all copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  * THE SOFTWARE.
 *
 */
package com.iluwatar.onion.domain.model;

import com.iluwatar.onion.domain.exception.DomainException;

public class Person {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final int age;
    private final String phoneNumber;
    private final String email;
    private final Category category;

    public Person(Long id, String firstName, String lastName, int age, String phoneNumber, String email, Category category) {
        validateNames(firstName, lastName);
        validateAge(age);
        validatePhone(phoneNumber);
        validateEmail(email);
        validateCategory(category);

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.category = category;
    }

    private void validateNames(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            throw new DomainException("First name and last name cannot be null.");
        }
    }

    private void validateAge(int age) {
        if (age < 18) {
            throw new DomainException("Age cannot be less than 18.");
        }
    }

    private void validatePhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            throw new DomainException("Phone number cannot be null or empty.");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new DomainException("Email cannot be null or empty.");
        }
    }

    private void validateCategory(Category category) {
        if (category == null || category.getType().isEmpty()) {
            throw new DomainException("Category cannot be null or empty.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Category getCategory() {
        return category;
    }
}
