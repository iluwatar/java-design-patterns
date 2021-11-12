package com.iluwatar.tupletable;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Types;

@Slf4j
public class MemberTupleDAO {
    /**
     * method to find the member based on the input param
     *
     * @param memberNo
     * @return member
     */
    public MemberDTO findMember(long memberNo) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs;
        MemberDTO member = new MemberDTO();
        member.setMemberNumber(memberNo);
        DBConnection db = new DBConnection();

        try {
            con = db.getConnection();
            ps = con.prepareStatement("select fieldname, numerical, string from object_data where obj_pk = ?");
            ps.setLong(1, memberNo);
            rs = ps.executeQuery();

            while (rs.next()) {
                String fieldName = rs.getString(1);
                String strVal = rs.getString(3);
                if (strVal != null) {
                    setVal(fieldName, member, strVal);
                } else {
                    //we do this indirectly to make database typecasting more reliable
                    long lngVal = rs.getLong(2);
                    if (!rs.wasNull()) {
                        setVal(fieldName, member, lngVal);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException | IOException e) {
            LOGGER.error(e.getMessage());
            return null;
        } finally {
            db.closeConnection(con);
            db.closeStatement(ps);
        }
        rs.close();
        return member;
    }

    /**
     * @param
     * @should save member in the DB
     */
    public void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS object_data (\n" +
                "OBJ_PK int NOT NULL,\n" +
                "FIELDNAME varchar(20) NOT NULL,\n" +
                "NUMERICAL int,\n" +
                "STRING varchar(255)\n" +
                ");";
        DBConnection db = new DBConnection();
        try (Connection con = db.getConnection();
             Statement stmt = con.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * @param member
     * @should save member in the DB
     */
    public void saveMember(MemberDTO member) {
        Connection con = null;
        PreparedStatement ps = null;
        DBConnection db = new DBConnection();

        long memberNo = member.getMemberNumber();
        if (memberNo >= 1) {
            try {
                con = db.getConnection();
                ps = con.prepareStatement("delete from object_data where obj_pk = ?");
                ps.setLong(1, memberNo);
                ps.executeUpdate();
                ps = con.prepareStatement("insert into object_data (obj_pk, fieldname, numerical, string) values (?,?,?,?);");
                ps.setLong(1, memberNo);
                extracted(member, ps);
            } catch (SQLException | ClassNotFoundException | IOException e) {
                LOGGER.error(e.getMessage());
            } finally {
                db.closeConnection(con);
                db.closeStatement(ps);
            }
        }
    }

    /**
     * @param member
     * @param ps
     * @throws SQLException
     * @should execute the prepared statement
     */
    private void extracted(MemberDTO member, PreparedStatement ps) throws SQLException {
        Method[] methods = member.getClass().getMethods();
        for (Method method : methods) {
            String mName = method.getName();
            if (mName.startsWith("get")) {
                try {
                    if (method.getReturnType() == String.class) {
                        ps.setString(2, mName.substring(3));
                        ps.setNull(3, Types.NUMERIC);
                        ps.setString(4, (String) method.invoke(member, new Object[]{}));
                        ps.executeUpdate();
                    } else if (method.getReturnType() == Long.class) {
                        ps.setString(2, mName.substring(3));
                        ps.setObject(3, (Long) method.invoke(member, new Object[]{}), Types.NUMERIC);
                        ps.setNull(4, Types.VARCHAR);
                        ps.executeUpdate();
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        }
    }


    /**
     * @param fieldName
     * @param target
     * @param param
     * @should set the value of the filed name
     */
    private void setVal(String fieldName, Object target, Object param) {
        try {
            Class<?> targetClass = target.getClass();
            Method setter = targetClass.getMethod("set" + fieldName, param.getClass());
            setter.invoke(target, param);
        } catch (NoSuchMethodException ignored) {
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
