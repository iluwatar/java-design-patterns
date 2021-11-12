package com.iluwatar.tupletable;

import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@Slf4j
public class AppTest {
    /**
     * Test for testing the connection ensuring the code works as per design
     */
    @Test
    public void shouldExecuteWithoutException() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }

    /**
     * Test for findMember and saveMember methods for MemberTupleDAO
     *
     * @throws SQLException
     */

    @Test
    public void testFindSetSave() throws SQLException {
        MemberTupleDAO mtd = new MemberTupleDAO();
        mtd.createTableIfNotExists();
        MemberDTO member = mtd.findMember(1);
        LOGGER.info(member.getFirstname() + " " + member.getLastname());
        LOGGER.info(String.valueOf(member.getFreePasses()));
        LOGGER.info(member.getCity());
        LOGGER.info(member.getAddress1());
        member.setMemberNumber(4);
        member.setFirstname("Atif");
        mtd.saveMember(member);
        member = mtd.findMember(4);
        LOGGER.info(member.getFirstname() + " " + member.getLastname());
    }
}
