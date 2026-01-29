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

// ABOUTME: JPA entity representing a wizard who can own multiple spellbooks.
// ABOUTME: Defines the owning side of the many-to-many relationship with Spellbook.
package com.iluwatar.servicelayer.wizard

import com.iluwatar.servicelayer.common.BaseEntity
import com.iluwatar.servicelayer.spellbook.Spellbook
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

/**
 * Wizard entity.
 */
@Entity
@Table(name = "WIZARD")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
class Wizard() : BaseEntity() {

    @Id
    @GeneratedValue
    @Column(name = "WIZARD_ID")
    override var id: Long? = null

    override var name: String? = null

    @ManyToMany(cascade = [CascadeType.ALL])
    var spellbooks: MutableSet<Spellbook> = HashSet()

    constructor(name: String?) : this() {
        this.name = name
    }

    fun addSpellbook(spellbook: Spellbook?) {
        spellbook?.wizards?.add(this)
        spellbook?.let { spellbooks.add(it) }
    }

    override fun toString(): String = name ?: ""
}
