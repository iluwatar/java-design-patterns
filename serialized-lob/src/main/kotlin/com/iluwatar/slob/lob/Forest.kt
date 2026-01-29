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

// ABOUTME: Data class representing a Forest entity containing animals and plants.
// ABOUTME: Root object graph for serialization demonstrating the Serialized LOB pattern.
package com.iluwatar.slob.lob

import com.iluwatar.slob.lob.Animal.Companion.iterateXmlForAnimalAndPlants
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.Serializable
import javax.xml.parsers.DocumentBuilderFactory

/**
 * Creates an object Forest which contains animals and plants as its constituents. Animals may eat
 * plants or other animals in the forest.
 */
data class Forest(
    var name: String = "",
    var animals: Set<Animal> = HashSet(),
    var plants: Set<Plant> = HashSet()
) : Serializable {

    companion object {
        const val HORIZONTAL_DIVIDER = "\n--------------------------\n"
    }

    /**
     * Provides the representation of Forest in XML form.
     *
     * @return XML Element
     */
    fun toXmlElement(): Element {
        val xmlDoc = getXmlDoc()

        val forestXml = xmlDoc.createElement("Forest")
        forestXml.setAttribute("name", name)

        val animalsXml = xmlDoc.createElement("Animals")
        for (animal in animals) {
            val animalXml = animal.toXmlElement(xmlDoc)
            animalsXml.appendChild(animalXml)
        }
        forestXml.appendChild(animalsXml)

        val plantsXml = xmlDoc.createElement("Plants")
        for (plant in plants) {
            val plantXml = plant.toXmlElement(xmlDoc)
            plantsXml.appendChild(plantXml)
        }
        forestXml.appendChild(plantsXml)
        return forestXml
    }

    /**
     * Returns XMLDoc to use for XML creation.
     *
     * @return XML DOC Object
     */
    private fun getXmlDoc(): Document {
        return DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder().newDocument()
    }

    /**
     * Parses the Forest Object from the input XML Document.
     *
     * @param document the XML document from which the Forest is to be parsed
     */
    fun createObjectFromXml(document: Document) {
        name = document.documentElement.getAttribute("name")
        val nodeList = document.getElementsByTagName("*")
        val mutableAnimals = animals.toMutableSet()
        val mutablePlants = plants.toMutableSet()
        iterateXmlForAnimalAndPlants(nodeList, mutableAnimals, mutablePlants)
        animals = mutableAnimals
        plants = mutablePlants
    }

    override fun toString(): String {
        val sb = StringBuilder("\n")
        sb.append("Forest Name = ").append(name).append("\n")
        sb.append("Animals found in the ").append(name).append(" Forest: \n")
        for (animal in animals) {
            sb.append(HORIZONTAL_DIVIDER)
            sb.append(animal.toString())
            sb.append(HORIZONTAL_DIVIDER)
        }
        sb.append("\n")
        sb.append("Plants in the ").append(name).append(" Forest: \n")
        for (plant in plants) {
            sb.append(HORIZONTAL_DIVIDER)
            sb.append(plant.toString())
            sb.append(HORIZONTAL_DIVIDER)
        }
        return sb.toString()
    }
}
