package com.iluwatar.servicelayer.common;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * 
 * Base class for entities.
 *
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity {

  @Version
  private Long version;

  /**
   * Indicates the unique id of this entity
   *
   * @return The id of the entity, or 'null' when not persisted
   */
  public abstract Long getId();

  /**
   * Set the id of this entity
   *
   * @param id The new id
   */
  public abstract void setId(Long id);

  /**
   * Get the name of this entity
   *
   * @return The name of the entity
   */
  public abstract String getName();

  /**
   * Set the name of this entity
   *
   * @param name The new name
   */
  public abstract void setName(final String name);

}
