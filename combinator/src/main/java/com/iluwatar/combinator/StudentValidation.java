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

package com.iluwatar.combinator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

final class ValidationSupport {
    private static final ValidationResult valid = new ValidationResult(){
        public boolean isValid(){ return true; }
        public Optional<String> getReason(){ return Optional.empty(); }
    };

    static ValidationResult valid(){
        return valid;
    }
}

interface ValidationResult{
    static ValidationResult valid(){
        return ValidationSupport.valid();
    }

    static ValidationResult invalid(String reason){
        return new Invalid(reason);
    }

    boolean isValid();

    Optional<String> getReason();
}

final class Invalid implements ValidationResult {

    private final String reason;

    Invalid(String reason){
        this.reason = reason;
    }

    public boolean isValid(){
        return false;
    }

    public Optional<String> getReason(){
        return Optional.of(reason);
    }
}

public interface StudentValidation extends Function<Student, ValidationResult> {
	static StudentValidation nameIsNotEmpty() {
        return holds(student -> !student.name.trim().isEmpty(), "Name is empty.");
    }

    static StudentValidation surnameIsNotEmpty() {
    	return holds(student -> !student.surname.trim().isEmpty(), "Surname is empty.");
    }

    static StudentValidation dateIsValid() {
    	return holds(student -> CheckDate.isValid(student.birthDate), "Invalid birth date.");
    }

    static StudentValidation eMailContainsAtSign() {
        return holds(student -> student.email.contains("@"), "Invalid e-mail. Missing @-sign.");
    }

    static StudentValidation isProgrammValid() {
    	return holds(student -> Arrays.stream(Programm.values()).anyMatch((t) -> t.name().equals(student.stProgramm)), "Invalid student programm.");
    }

    static StudentValidation holds(Predicate<Student> p, String message){
        return student -> p.test(student) ? ValidationResult.valid() : ValidationResult.invalid(message);
    }

    default StudentValidation and(StudentValidation other) {
        return student -> {
            final ValidationResult result = this.apply(student);
            return result.isValid() ? other.apply(student) : result;
        };
    }
}

class CheckDate {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    static{
        DATE_FORMAT.setLenient(true);
    }


    public static boolean isValid(String date){
        try {
            return DATE_FORMAT.format(DATE_FORMAT.parse(date)).equals(date);
        }catch (ParseException ex){
            return false;
        }
    }
}