package com.iluwatar;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="SPELLBOOK")
public class Spellbook extends BaseEntity {
	
	public Spellbook() {
		spells = new HashSet<Spell>();
	}
	
	public Spellbook(String name) {
		this();
		this.name = name;
	}

	private String name;

	@ManyToOne
	@JoinColumn(name="WIZARD_ID_FK", referencedColumnName="ID")
	private Wizard wizard;

	@OneToMany(mappedBy = "spellbook", orphanRemoval = true, cascade = CascadeType.ALL)
	private Set<Spell> spells;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Wizard getWizard() {
		return wizard;
	}

	public void setWizard(Wizard wizard) {
		this.wizard = wizard;
	}

	public Set<Spell> getSpells() {
		return spells;
	}

	public void setSpells(Set<Spell> spells) {
		this.spells = spells;
	}
	
	@Override
	public String toString() {
		return name;
	}	
}
