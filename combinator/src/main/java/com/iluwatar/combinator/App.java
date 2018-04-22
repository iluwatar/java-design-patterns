package com.iluwatar.combinator;

public class App {

	public static void main(String[] args) {
		StudentValidation validation = StudentValidation.nameIsNotEmpty().and(StudentValidation.surnameIsNotEmpty()).and(StudentValidation.eMailContainsAtSign().and(StudentValidation.dateIsValid()).and(StudentValidation.isProgrammValid()));
		Student student = new Student("Ivan", "Ivanov", "04.06.1997", "ivanov@gmail.com", "Mathematics");

		ValidationResult result = validation.apply(student);
		result.getReason().ifPresent(System.out::println);
	}

}