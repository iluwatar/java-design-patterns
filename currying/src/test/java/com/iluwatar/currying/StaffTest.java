package com.iluwatar.currying;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

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
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void createStaffWithFunctionalInterface() {
        Staff actualResult = Staff.builder()
                .withReturnFirstName(firstName)
                .withReturnLastName(lastName)
                .withReturnGender(gender)
                .withReturnEmail(email)
                .withReturnDateOfBirth(dateOfBirth);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void mainTest() {
        Staff.main(new String[]{});
    }

    @Test
    public void hashTest() {
        expectedResult.hashCode();
    }

    @Test
    public void equalTest() {
        assertTrue(expectedResult.equals(expectedResult));
        assertFalse(expectedResult.equals(new Integer(1)));
        Staff o2 = new Staff(firstName, lastName, gender, email, dateOfBirth);
        assertTrue(expectedResult.equals(o2));
    }
}
