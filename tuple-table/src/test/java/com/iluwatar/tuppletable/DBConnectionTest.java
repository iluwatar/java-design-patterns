package com.iluwatar.tuppletable;

import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConnectionTest {

    /**
     *
     * Test for public methods for MemberTupleDAO
     * @throws SQLException
     */

    @Test
    public void testForPublicMethods() throws SQLException, IOException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();
        db.getConnection();
        db.closeConnection(con, ps);

    }

}