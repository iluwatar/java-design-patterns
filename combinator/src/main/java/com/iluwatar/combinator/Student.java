package com.iluwatar.combinator;


public class Student {
	public final String name;
	public final String surname;
	public final String birthDate;
	public final String email;
	public String stProgramm;

	public Student (String name, String surname, String birthDate, String email, String stProgramm) {
		this.name = name;
		this.surname = surname;
		this.birthDate = birthDate;
		this.email = email;
		this.stProgramm = stProgramm;
	}
}
