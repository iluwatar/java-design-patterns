package entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "player")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Player {
String name;
}
