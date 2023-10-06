package com.iluwatar.repository.impl;

import com.iluwatar.Person;
import com.iluwatar.repository.PersonRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


/**
 * An implementation of the `PersonRepository` interface that uses a relational database to store and retrieve `Person` objects.
 *
 * @author ASHUdev05
 */
public class PersonRepositoryImpl implements PersonRepository {

  /**
   * The `JdbcTemplate` object used to interact with the relational database.
   */
  private final JdbcTemplate jdbcTemplate;

  /**
   * Constructor.
   *
   * @param jdbcTemplate the `JdbcTemplate` object to use
   */
  public PersonRepositoryImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Person save(Person person) {
    String sql = "INSERT INTO person (name, age) VALUES (?, ?)";
    jdbcTemplate.update(sql, person.getName(), person.getAge());
    return person;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<Person> findById(Long id) {
    String sql = "SELECT * FROM person WHERE id = ?";
    return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new PersonMapper(), id));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Person> findAll() {
    String sql = "SELECT * FROM person";
    return jdbcTemplate.query(sql, new PersonMapper());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteById(Long id) {
    String sql = "DELETE FROM person WHERE id = ?";
    jdbcTemplate.update(sql, id);
  }

  /**
   * A `RowMapper` that maps a row in the `person` table to a `Person` object.
   */
  public static class PersonMapper implements RowMapper<Person> {

    /**
     * Maps a row in the `person` table to a `Person` object.
     *
     * @param rs     the `ResultSet` containing the row to map
     * @param rowNum the row number
     * @return the mapped `Person` object
     * @throws SQLException if an error occurs while mapping the row
     */
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
      Person person = new Person();
      person.setId(rs.getLong("id"));
      person.setName(rs.getString("name"));
      person.setAge(rs.getInt("age"));
      return person;
    }
  }
}
