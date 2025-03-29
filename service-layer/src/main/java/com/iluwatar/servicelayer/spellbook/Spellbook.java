/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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

import com.iluwatar.servicelayer.common.BaseEntity;
import com.iluwatar.servicelayer.spell.Spell;
import com.iluwatar.servicelayer.wizard.Wizard;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/** Spellbook entity. */
@Entity
@Table(name = "SPELLBOOK")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
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

  public void addSpell(Spell spell) {
    spell.setSpellbook(this);
    spells.add(spell);
  }

  @Override
  public String toString() {
    return name;
  }
}
