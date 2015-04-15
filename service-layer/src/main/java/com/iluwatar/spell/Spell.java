package com.iluwatar.spell;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.iluwatar.common.BaseEntity;
import com.iluwatar.spellbook.Spellbook;

/**
 * 
 * Spell entity.
 *
 */
@Entity
@Table(name="SPELL")
public class Spell extends BaseEntity {
	
	private String name;
	
	public Spell() {
	}
	
	public Spell(String name) {
		this();
		this.name = name;
	}

	@Id
	@GeneratedValue
	@Column(name = "SPELL_ID")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="SPELLBOOK_ID_FK", referencedColumnName="SPELLBOOK_ID")
	private Spellbook spellbook;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Spellbook getSpellbook() {
		return spellbook;
	}

	public void setSpellbook(Spellbook spellbook) {
		this.spellbook = spellbook;
	}

	@Override
	public String toString() {
		return name;
	}	
}
