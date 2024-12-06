package com.iluwater.fieild;


import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@MappedSuperclass
public abstract class DomainObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // automatically generate unique values for primary key columns
    //strategy = GenerationType.IDENTITY generate the primary key value by the database itself using the auto-increment column option
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
