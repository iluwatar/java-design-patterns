
/*
 * The DomainObject class provides a base for domain entities
 * that require unique identification within the application.
 *
 * <p>All child classes inherit the unique identifier field
 * and associated functionality.</p>
 */
package com.iluwater.fieild;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@MappedSuperclass
public abstract class DomainObject {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // automatically generate unique values for primary key columns
  //strategy = GenerationType.IDENTITY generate the primary key value by the database itself using the auto-increment column option
  private Integer id;

}
