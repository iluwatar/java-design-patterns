package com.memory.dao.db;

import com.memory.dao.pojo.User;
import java.util.List;

public interface UserDAO {
	
	User findByName(String name);
	List<User> findAll();
	
}
