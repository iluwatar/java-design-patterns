package com.iluwatar;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="SPELL")
public class Spell extends BaseEntity {
	
	private String name;

	@ManyToOne
	@JoinColumn(name="SPELLBOOK_ID_FK", referencedColumnName="ID")
	private Spellbook spellbook;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
