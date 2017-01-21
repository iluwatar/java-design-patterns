package com.memory.dao.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.memory.dao.pojo.User;

@Repository
public class UserDAOImpl implements UserDAO {
	
	private NamedParameterJdbcTemplate namedParameterJDBCTemplate;

	@Autowired
	public void setNamedParameterJDBCTemplate(final NamedParameterJdbcTemplate namedParameterJDBCTemplate) {
		this.namedParameterJDBCTemplate = namedParameterJDBCTemplate;
	}

	@Override
	public User findByName(final String name) {
		final Map<String, Object> params = new HashMap<>();
		params.put("name", name);
		
		final User user = namedParameterJDBCTemplate.
				queryForObject(Queries.GET_USER.get(), params, new UserMapper());
		
		System.out.println("Found: " + user);
		
		return user;
	}
	
	@Override
	public List<User> findAll() {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
        final List<User> result = namedParameterJDBCTemplate.query(Queries.GET_ALL_USERS.get(), params, new UserMapper());
        
        return result;
        
	}
	
	private static final class UserMapper implements RowMapper<User> {
		@Override
		public User mapRow(final ResultSet rs, final int rowNum) throws SQLException {
			final User user = new User();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			return user;
		}
	}
	
}
