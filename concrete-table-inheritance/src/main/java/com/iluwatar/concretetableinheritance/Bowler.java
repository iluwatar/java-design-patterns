package com.iluwatar.concretetableinheritance;

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
   * The average bowling number.
   */
  private double bowlingAverage;
}
