package com.iluwatar.tupletable;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import lombok.extern.slf4j.Slf4j;

/**
 * This class implements MemberTupleDao. The class contains following methods:
 * findMember() - is used to find the member stored in the databased based on the query.
 * createTableIfNotExists() - creates the table for storing object_data if not exists.
 * saveMember() - is used to store the value of the fields being passed.
 * extracted() - is an extension of the saveMember() method.
 * setVal() - uses reflection to invoke the target method.
 */

@Slf4j
public class MemberTupleDao {

  private final Database db = new Database();

  /**
   * Method implemented to find the member based on the input param.
   *
   * @param memberNo is key with which tuple record is looked up
   * @return member retrieved from the database
   */
  public MemberDto findMember(long memberNo) throws SQLException, ClassNotFoundException {
    var member = new MemberDto();
    member.setMemberNumber(memberNo);

    try (var con = db.getConnection();
         var ps = con.prepareStatement("select fieldname, numerical, string "
                 + "from object_data where obj_pk = ?")) {
      ps.setLong(1, memberNo);
      try (var rs = ps.executeQuery()) {
        while (rs.next()) {
          var fieldName = rs.getString(1);
          var strVal = rs.getString(3);
          if (strVal != null) {
            setVal(fieldName, member, strVal);
          } else {
            //we do this indirectly to make database typecasting more reliable
            var lngVal = rs.getLong(2);
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

    try (var con = db.getConnection();
         var stmt = con.createStatement()) {
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
    var memberNo = member.getMemberNumber();
    if (memberNo >= 1) {
      try (var con = db.getConnection()) {
        try (var ps = con.prepareStatement(
                "delete from object_data where obj_pk = ?");) {
          ps.setLong(1, memberNo);
          ps.executeUpdate();
          try (var ps1 = con.prepareStatement(
                  "insert into object_data (obj_pk, fieldname, "
                          + "numerical, string) values (?,?,?,?);");
          ) {
            ps1.setLong(1, memberNo);
            saveMemberBaseOnDtoClass(member, ps1);
          }
        }
      }
    }
  }

  /**
   * Extracted method for saveMember method. Here based on the class method of DTO the ps statement is set differently
   * for long and string
   *
   * @param member object
   * @param ps     as prepared statement
   * @throws SQLException if encounters any violation while updating record in database.
   * @should execute the prepared statement
   */
  private void saveMemberBaseOnDtoClass(MemberDto member, PreparedStatement ps) throws SQLException,
          IllegalAccessException, InvocationTargetException {
    var methods = member.getClass().getMethods();
    for (var method : methods) {
      var methodName = method.getName();
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
   * Set a value on the target object, by searching for a set<fieldName> method which takes a parameter of the same
   * value as the "param" parameter.
   *
   * @param fieldName as name of the field for a tuple
   * @param target    as member object
   * @param param     as value of the field
   * @should set the value of the filed name
   */
  private void setVal(String fieldName, Object target, Object param) {
    try {
      var targetClass = target.getClass();
      var setter = targetClass.getMethod("set" + fieldName, param.getClass());
      setter.invoke(target, param);
    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
      LOGGER.error(e.getMessage());
    }
  }
}
