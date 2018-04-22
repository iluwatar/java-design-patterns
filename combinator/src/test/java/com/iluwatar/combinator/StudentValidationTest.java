package com.iluwatar.combinator;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;

public class StudentValidationTest {

	StudentValidation validation = StudentValidation.nameIsNotEmpty().and(StudentValidation.surnameIsNotEmpty()).and(StudentValidation.eMailContainsAtSign().and(StudentValidation.dateIsValid()).and(StudentValidation.isProgrammValid()));

	@Test
	public void testWorks() {
		Student student = new Student("Ivan", "Ivanov", "04.06.1997", "ivanov@gmail.com", "Mathematics");
		ValidationResult result = validation.apply(student);
		assertEquals(Optional.empty(), result.getReason());
	}

	@Test
	public void testNameIsEmpty() {
		Student student = new Student("", "Ivanov", "04.06.1997", "ivanov@gmail.com", "Mathematics");
		ValidationResult result = validation.apply(student);
		assertEquals(Optional.of("Name is empty."), result.getReason());
	}

	@Test
	public void testSurnameIsEmpty() {
		Student student = new Student("Ivan", "", "04.06.1997", "ivanov@gmail.com", "Mathematics");
		ValidationResult result = validation.apply(student);
		assertEquals(Optional.of("Surname is empty."), result.getReason());
	}

	@Test
	public void testDayIsNotValid() {
		Student student = new Student("Ivan", "Ivanov", "29.02.1997", "ivanov@gmail.com", "Mathematics");
		ValidationResult result = validation.apply(student);
		assertEquals(Optional.of("Invalid birth date."), result.getReason());
	}

	@Test
	public void testMonthIsNotValid() {
		Student student = new Student("Ivan", "Ivanov", "29.13.1997", "ivanov@gmail.com", "Mathematics");
		ValidationResult result = validation.apply(student);
		assertEquals(Optional.of("Invalid birth date."), result.getReason());
	}

	@Test
	public void testYearIsNotValid() {
		Student student = new Student("Ivan", "Ivanov", "29.12.19", "ivanov@gmail.com", "Mathematics");
		ValidationResult result = validation.apply(student);
		assertEquals(Optional.of("Invalid birth date."), result.getReason());
	}

	@Test
	public void testLeapYear() {
		Student student = new Student("Ivan", "Ivanov", "29.02.2016", "ivanov@gmail.com", "Mathematics");
		ValidationResult result = validation.apply(student);
		assertEquals(Optional.empty(), result.getReason());
	}

	@Test
	public void testEmailIsNotValid() {
		Student student = new Student("Ivan", "Ivanov", "04.06.1997", "ivanovgmail.com", "Mathematics");
		ValidationResult result = validation.apply(student);
		assertEquals(Optional.of("Invalid e-mail. Missing @-sign."), result.getReason());
	}

	@Test
	public void testProgrammIsNotValid() {
		Student student = new Student("Ivan", "Ivanov", "04.06.1997", "ivanov@gmail.com", "Mathemtics");
		ValidationResult result = validation.apply(student);
		assertEquals(Optional.of("Invalid student programm."), result.getReason());
	}

}
