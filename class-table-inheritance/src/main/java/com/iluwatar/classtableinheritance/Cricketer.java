package com.iluwatar.classtableinheritance;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * The class Cricketer is one member of class Player.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "cricketer")
public class Cricketer extends Player {
  /**
   * batting avarage.
   */
  private double battingAvarage;

}
