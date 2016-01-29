package com.iluwatar.servicelayer.spellbook;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.iluwatar.servicelayer.common.BaseEntity;
import com.iluwatar.servicelayer.spell.Spell;
import com.iluwatar.servicelayer.wizard.Wizard;

/**
 * 
 * Spellbook entity.
 *
 */
@Entity
@Table(name = "SPELLBOOK")
public class Spellbook extends BaseEntity {

  public Spellbook() {
    spells = new HashSet<>();
    wizards = new HashSet<>();
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

  @ManyToMany(mappedBy = "spellbooks", fetch = FetchType.EAGER)
  private Set<Wizard> wizards;

  @OneToMany(mappedBy = "spellbook", orphanRemoval = true, cascade = CascadeType.ALL)
  private Set<Spell> spells;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Wizard> getWizards() {
    return wizards;
  }

  public void setWizards(Set<Wizard> wizards) {
    this.wizards = wizards;
  }

  public Set<Spell> getSpells() {
    return spells;
  }

  public void setSpells(Set<Spell> spells) {
    this.spells = spells;
  }

  public void addSpell(Spell spell) {
    spell.setSpellbook(this);
    spells.add(spell);
  }

  @Override
  public String toString() {
    return name;
  }
}
