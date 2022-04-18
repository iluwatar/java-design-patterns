package entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;


/**
 * The class Cricketer is one member of class Player.
 */
@Data
@Entity
@Table(name = "cricketer")
public class Cricketer extends Player {
  double battingAvarage;

}
