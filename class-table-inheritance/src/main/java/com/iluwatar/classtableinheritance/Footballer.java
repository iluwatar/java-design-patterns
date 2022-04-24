package com.iluwatar.classtableinheritance;


import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Footbller class is one member of Player.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "footballer")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Footballer extends Player {
  /**
   * footballer 's club.
   */
  private String club;

}
