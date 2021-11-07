package com.iluwatar.tuppletable;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AppTest {
    /**
     *Test for testing the connection ensuring the code works as per design
     */
    @Test
    public void shouldExecuteWithoutException() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }

    /**
     *
     * Test for public methods for MemberTupleDAO
     * @throws SQLException
     */

    @Test
    public void testForPublicMethods() throws SQLException {
        MemberTupleDAO mtd = new MemberTupleDAO();
        MemberDTO member = mtd.findMember(1);
        System.out.println(member.getFirstname() + " " + member.getLastname());
        System.out.println(member.getFreePasses());
        System.out.println(member.getCity());
        System.out.println(member.getAddress1());
        member.setMemberNumber(4);
        member.setFirstname("Atif");
        mtd.saveMember(member);
        member = mtd.findMember(4);
        System.out.println(member.getFirstname() + " " + member.getLastname());
    }
}