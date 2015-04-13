package com.iluwatar;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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

	@Id
	@GeneratedValue
	@Column(name = "SPELLBOOK_ID")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	private String name;

	@ManyToOne
	@JoinColumn(name="WIZARD_ID_FK", referencedColumnName="WIZARD_ID")
	private Wizard wizard;

	@OneToMany(mappedBy = "spellbook", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
