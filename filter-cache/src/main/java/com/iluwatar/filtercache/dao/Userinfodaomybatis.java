package com.iluwatar.filtercache.dao;



import com.iluwatar.filtercache.entity.Userinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * it define real sql sentence.
 */
@Mapper
public interface Userinfodaomybatis {
  @Select("select * from user_info where id =#{idnum}")
  Userinfo getById(Integer idnum);
}
