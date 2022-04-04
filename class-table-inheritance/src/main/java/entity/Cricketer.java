package entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;


@Data
@Entity
@Table(name = "cricketer")
public class Cricketer extends Player{
double battingAvarage;

}
