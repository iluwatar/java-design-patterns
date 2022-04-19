package com.iluwatar.classtableinheritance;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import lombok.Data;

/**
 * Base class Player.
 */
@Data
@Entity
@Table(name = "player")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Player {
  String name;
}
