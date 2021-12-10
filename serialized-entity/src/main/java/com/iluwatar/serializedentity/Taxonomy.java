/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.serializedentity;

import lombok.*;

import java.io.Serializable;

/**
 * A Taxonomy object that represents the full zoological classification
 * of a living organism
 * The Latin classis is used instead of class since
 * class is a reserved name in Java
 */
@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Taxonomy implements Serializable {
    /**
     * id int for the database
     */
    private int speciesId;
    /**
     * Stores taxonomic rank Domain
     */
    private String domain;
    /**
     * Stores taxonomic rank Kingdom
     */
    private String kingdom;
    /**
     * Stores taxonomic rank Phylum
     */
    private String phylum;
    /**
     * Stores taxonomic rank Class
     */
    private String classis;
    /**
     * Stores taxonomic rank Order
     */
    private String order;
    /**
     * Stores taxonomic rank Family
     */
    private String family;
    /**
     * Stores taxonomic rank Genus
     */
    private String genus;
    /**
     * Stores taxonomic rank Species
     */
    private String species;
    public static final long serialVersionUID = 7846250;
}
