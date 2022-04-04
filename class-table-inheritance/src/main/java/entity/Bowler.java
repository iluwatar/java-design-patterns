package entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "bowler")
public class Bowler extends Cricketer{
    double bowlingAvarage;
}
