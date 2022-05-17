package com.iluwatar.concretetableinheritance;


import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Footballer class is one member of Player.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "footballer")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Footballer extends Player {
  /**
   * The club of the footballer.
   */
  private String club;

}
