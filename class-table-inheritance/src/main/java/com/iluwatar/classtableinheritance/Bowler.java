package com.iluwatar.classtableinheritance;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

/**
 * Bowler belongs to one member of Cricketer.
 */
@Entity
@Data
@Table(name = "bowler")
public class Bowler extends Cricketer {
  double bowlingAvarage;
}
