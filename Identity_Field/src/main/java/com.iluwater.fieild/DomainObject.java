package com.iluwater.fieild;

import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DomainObject {
  @lombok.Setter
  @lombok.Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // automatically generate unique values for primary key columns
    //strategy = GenerationType.IDENTITY generate the primary key value by the database itself using the auto-increment column option
    private Integer id;

}
