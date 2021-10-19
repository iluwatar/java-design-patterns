package com.iluwatar.currying;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class StaffTest {
    private final String firstName = "Janus";
    private final String lastName = "Lin";
    private final Staff.Gender gender = Staff.Gender.Male;
    private final String email = "example@gmail.com";
    private final LocalDate dateOfBirth = LocalDate.now();

    private final Staff expectedResult = new Staff(firstName, lastName, gender, email, dateOfBirth);

    @Test
    public void createStaffWithBasicCurrying() {
        Staff actualResult = Staff.CREATOR
                .apply(firstName)
                .apply(lastName)
                .apply(gender)
                .apply(email)
                .apply(dateOfBirth);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void createStaffWithFunctionalInterface() {
        Staff actualResult = Staff.builder()
                .withReturnFirstName(firstName)
                .withReturnLastName(lastName)
                .withReturnGender(gender)
                .withReturnEmail(email)
                .withReturnDateOfBirth(dateOfBirth);
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void mainTest() {
        Staff.main(new String[]{});
    }
}
