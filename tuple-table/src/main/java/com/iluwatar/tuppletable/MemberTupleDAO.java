package com.iluwatar.tuppletable;

import java.io.IOException;
import java.lang.reflect.*;
import java.sql.*;

public class MemberTupleDAO {
    /**
     * method to find the member based on the input param
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
            e.printStackTrace();
            return null;
        } finally {
            db.closeConnection(con, ps);
        }
        rs.close();
        return member;
    }

    /**
     * @should save member in the DB
     * @param member
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
                e.printStackTrace();
            } finally {
                db.closeConnection(con, ps);
            }
        }
    }

    /**
     * @should execute the prepared statement
     * @param member
     * @param ps
     * @throws SQLException
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
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * @should set the value of the filed name
     * @param fieldName
     * @param target
     * @param param
     */
    private void setVal(String fieldName, Object target, Object param) {
        try {
            Class<?> targetClass = target.getClass();
            Method setter = targetClass.getMethod("set" + fieldName, param.getClass());
            setter.invoke(target, param);
        } catch (NoSuchMethodException ignored) {
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
