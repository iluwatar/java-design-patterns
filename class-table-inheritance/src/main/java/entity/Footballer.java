package entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
@Data
@Entity
@Table(name = "footballer")
public class Footballer extends Player{
    String club;

}
