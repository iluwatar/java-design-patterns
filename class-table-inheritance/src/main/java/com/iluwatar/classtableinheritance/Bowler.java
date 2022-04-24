package com.iluwatar.classtableinheritance;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Bowler belongs to one member of Cricketer.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "bowler")
public class Bowler extends Cricketer {
  /**
   * this is a variable realated to bowling.
   */
  private double bowlingAvarage;
}
