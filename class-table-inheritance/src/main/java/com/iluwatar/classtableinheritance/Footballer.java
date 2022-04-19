package com.iluwatar.classtableinheritance;


import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import lombok.Data;

/**
 * Footbller class is one member of Player.
 */
@Data
@Entity
@Table(name = "footballer")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Footballer extends Player {
  String club;

}
