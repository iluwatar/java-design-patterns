package com.iluwatar.tupletable;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class AppTest {
  /**
   * Test for testing the connection ensuring the code works as per design
   */
  @Test
  void shouldExecuteWithoutException() {
    assertDoesNotThrow(() -> App.main(new String[]{}));
  }

  /**
   * Test for findMember and saveMember methods for MemberTupleDAO
   *
   * @throws SQLException for db interaction of connection, save or get information
   */

  @Test
  void testFindSetSave() throws SQLException, ClassNotFoundException, InvocationTargetException,
          IllegalAccessException {
    MemberTupleDao mtd = new MemberTupleDao();
    mtd.createTableIfNotExists();
    MemberDto member = mtd.findMember(1);

    if (LOGGER.isInfoEnabled()) {
      LOGGER.info(member.getFirstName() + " " + member.getLastName()
              + "\n" + member.getFreePasses()
              + "\n" + member.getCity()
              + "\n" + member.getAddress1());
    }
    member.setMemberNumber(4);
    member.setFirstName("Atif");
    mtd.saveMember(member);
    member = mtd.findMember(4);
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info(member.getFirstName() + " " + member.getLastName());
    }
    assertEquals(member.getFirstName(), "Atif");
  }
}
