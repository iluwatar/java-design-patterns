package com.iluwatar.tupletable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MemberTupleDao {
  /**
   * Method implemented to find the member based on the input param.
   *
   * @param memberNo is key with which tuple record is looked up
   * @return member retrieved from the database
   */
  public MemberDto findMember(long memberNo) throws SQLException, ClassNotFoundException {
    Database db = new Database();
    MemberDto member = new MemberDto();
    member.setMemberNumber(memberNo);

    try (Connection con = db.getConnection();
         PreparedStatement ps = con.prepareStatement("select fieldname, numerical, string "
                 + "from object_data where obj_pk = ?")) {
      // create a new table
      ps.setLong(1, memberNo);
      try (ResultSet rs = ps.executeQuery()) {
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
      }
    }
    return member;
  }

  /**
   * Method to create the table if not exist (for demo purpose).
   *
   * @should create the object_data table when called
   */
  public void createTableIfNotExists() throws SQLException, ClassNotFoundException {
    String sql = "CREATE TABLE IF NOT EXISTS object_data (\n"
            + "OBJ_PK int NOT NULL,\n"
            + "FIELDNAME varchar(20) NOT NULL,\n"
            + "NUMERICAL int,\n"
            + "STRING varchar(255)\n"
            + ");";
    Database db = new Database();
    try (Connection con = db.getConnection();
         Statement stmt = con.createStatement()) {
      // create a new table
      stmt.execute(sql);
    }
  }

  /**
   * Method implemented to save the member in database.
   *
   * @param member object
   * @should save member in the DB
   */
  public void saveMember(MemberDto member) throws SQLException, ClassNotFoundException,
          InvocationTargetException, IllegalAccessException {
    Database db = new Database();
    long memberNo = member.getMemberNumber();
    if (memberNo >= 1) {
      try (Connection con = db.getConnection()) {
        // create a new table
        try (PreparedStatement ps = con.prepareStatement(
                "delete from object_data where obj_pk = ?");) {
          ps.setLong(1, memberNo);
          ps.executeUpdate();
          try (PreparedStatement ps1 = con.prepareStatement(
                  "insert into object_data (obj_pk, fieldname, "
                          + "numerical, string) values (?,?,?,?);");
          ) {
            ps1.setLong(1, memberNo);
            extracted(member, ps1);
          }
        }
      }
    }
  }

  /**
   * Extracted method for saveMember method.
   *
   * @param member object
   * @param ps     as prepared statement
   * @throws SQLException if encounters any violation while updating record in database.
   * @should execute the prepared statement
   */
  private void extracted(MemberDto member, PreparedStatement ps) throws SQLException,
          IllegalAccessException, InvocationTargetException {
    Method[] methods = member.getClass().getMethods();
    for (Method method : methods) {
      String methodName = method.getName();
      if (methodName.startsWith("get")) {
        if (method.getReturnType() == String.class) {
          ps.setString(2, methodName.substring(3));
          ps.setNull(3, Types.NUMERIC);
          ps.setString(4, (String) method.invoke(member));
          ps.executeUpdate();
        } else if (method.getReturnType() == long.class) {
          ps.setString(2, methodName.substring(3));
          ps.setObject(3, method.invoke(member), Types.NUMERIC);
          ps.setNull(4, Types.VARCHAR);
          ps.executeUpdate();
        }
      }
    }
  }


  /**
   * Method implemented to set the value for a field name.
   *
   * @param fieldName as name of the field for a tuple
   * @param target    as member object
   * @param param     as value of the field
   * @should set the value of the filed name
   */
  private void setVal(String fieldName, Object target, Object param) {
    try {
      Class<?> targetClass = target.getClass();
      Method setter = targetClass.getMethod("set" + fieldName, param.getClass());
      setter.invoke(target, param);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      if (LOGGER.isErrorEnabled()) {
        LOGGER.error(e.getMessage());
      }
    }
  }
}
