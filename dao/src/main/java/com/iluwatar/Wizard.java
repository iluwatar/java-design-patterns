package com.iluwatar;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="WIZARD")
public class Wizard extends BaseEntity {
	
	public Wizard() {
		spellbooks = new HashSet<Spellbook>();
	}
	
	private String firstName;

	@OneToMany(mappedBy = "wizard", orphanRemoval = true, cascade = CascadeType.ALL)
	private Set<Spellbook> spellbooks;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Set<Spellbook> getSpellbooks() {
		return spellbooks;
	}

	public void setSpellbooks(Set<Spellbook> spellbooks) {
		this.spellbooks = spellbooks;
	}	
}
