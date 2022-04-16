package entity;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "bowler")
public class Bowler extends Cricketer{
    double bowlingAvarage;
}
