/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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

  @Id
  @GeneratedValue
  @Column(name = "SPELLBOOK_ID")
  private Long id;

  private String name;

  @ManyToMany(mappedBy = "spellbooks", fetch = FetchType.EAGER)
  private Set<Wizard> wizards;

  @OneToMany(mappedBy = "spellbook", orphanRemoval = true, cascade = CascadeType.ALL)
  private Set<Spell> spells;

  public Spellbook() {
    spells = new HashSet<>();
    wizards = new HashSet<>();
  }

  public Spellbook(String name) {
    this();
    this.name = name;
  }
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
  
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
