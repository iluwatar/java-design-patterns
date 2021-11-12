package com.iluwatar.tupletable;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

public class DBConnectionTest {

    /**
     * Test for public methods for MemberTupleDAO
     *
     * @throws SQLException
     */

    @Test
    public void testDBConnectionAndClose() throws SQLException, IOException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();
        db.getConnection();
        db.closeConnection(con);
        db.closeStatement(ps);
    }
}
