package entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
@Data
@Entity
@Table(name = "footballer")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Footballer extends Player{
    String club;

}
