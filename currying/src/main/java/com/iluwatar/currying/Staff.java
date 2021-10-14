package com.iluwatar.currying;

import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Function;

public class Staff {
    private String firstName;
    private String lastName;
    private Gender gender;
    private String email;
    private LocalDate dateOfBirth;

    public Staff(String firstName, String lastName, Gender gender, String email, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return firstName.equals(staff.firstName) && lastName.equals(staff.lastName) && gender == staff.gender && email.equals(staff.email) && dateOfBirth.equals(staff.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, gender, email, dateOfBirth);
    }

    static Function<String, Function<String, Function<Gender, Function<String, Function<LocalDate, Staff>>>>> CREATOR =
            firstName -> lastName -> gender -> email -> dateOfBirth -> new Staff(firstName, lastName, gender, email, dateOfBirth);

    static AddFirstName builder() {
        return firstName -> lastName -> gender -> email -> dateOfBirth -> new Staff(firstName, lastName, gender, email, dateOfBirth);
    }

    interface AddFirstName {
        AddLastName withReturnFirstName(String firstName);
    }

    interface AddLastName {
        AddGender withReturnLastName(String lastName);
    }

    interface AddGender {
        AddEmail withReturnGender(Gender gender);
    }

    interface AddEmail {
        AddDateOfBirth withReturnEmail(String email);
    }

    interface AddDateOfBirth {
        Staff withReturnDateOfBirth(LocalDate dateOfBirth);
    }

    enum Gender {
        Male, Female
    }
}
