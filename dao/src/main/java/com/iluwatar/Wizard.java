package com.iluwatar;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="WIZARD")
public class Wizard extends BaseEntity {
	
	public Wizard() {
		spellbooks = new HashSet<Spellbook>();
	}
	
	public Wizard(String name) {
		this();
		this.name = name;
	}

	@Id
	@GeneratedValue
	@Column(name = "WIZARD_ID")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	private String name;

	@OneToMany(mappedBy = "wizard", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Spellbook> spellbooks;
	
	public String getFirstName() {
		return name;
	}

	public void setFirstName(String firstName) {
		this.name = firstName;
	}

	public Set<Spellbook> getSpellbooks() {
		return spellbooks;
	}

	public void setSpellbooks(Set<Spellbook> spellbooks) {
		this.spellbooks = spellbooks;
	}

	@Override
	public String toString() {
		return name;
	}	
}
