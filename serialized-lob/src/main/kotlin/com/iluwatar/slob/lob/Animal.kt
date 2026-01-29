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

// ABOUTME: Data class representing an Animal entity in the forest ecosystem.
// ABOUTME: Supports XML serialization/deserialization with relationships to other animals and plants.
package com.iluwatar.slob.lob

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.Serializable

/**
 * Creates an object Animal with a list of animals and/or plants it consumes.
 */
data class Animal(
    var name: String = "",
    var plantsEaten: Set<Plant> = HashSet(),
    var animalsEaten: Set<Animal> = HashSet()
) : Serializable {

    companion object {
        /**
         * Iterates over the input nodes recursively and adds new plants to [plantsEaten] or
         * animals to [animalsEaten] found to input sets respectively.
         *
         * @param childNodes contains the XML Node containing the Forest
         * @param animalsEaten set of Animals eaten
         * @param plantsEaten set of Plants eaten
         */
        @JvmStatic
        internal fun iterateXmlForAnimalAndPlants(
            childNodes: NodeList,
            animalsEaten: MutableSet<Animal>,
            plantsEaten: MutableSet<Plant>
        ) {
            for (i in 0 until childNodes.length) {
                val child = childNodes.item(i)
                if (child.nodeType == Node.ELEMENT_NODE) {
                    when (child.nodeName) {
                        Animal::class.simpleName -> {
                            val animalEaten = Animal()
                            animalEaten.createObjectFromXml(child)
                            animalsEaten.add(animalEaten)
                        }
                        Plant::class.simpleName -> {
                            val plant = Plant()
                            plant.createObjectFromXml(child)
                            plantsEaten.add(plant)
                        }
                    }
                }
            }
        }
    }

    /**
     * Provides XML Representation of the Animal.
     *
     * @param xmlDoc object to which the XML representation is to be written to
     * @return XML Element contain the Animal representation
     */
    fun toXmlElement(xmlDoc: Document): Element {
        val root = xmlDoc.createElement(Animal::class.simpleName)
        root.setAttribute("name", name)
        for (plant in plantsEaten) {
            val xmlElement = plant.toXmlElement(xmlDoc)
            root.appendChild(xmlElement)
        }
        for (animal in animalsEaten) {
            val xmlElement = animal.toXmlElement(xmlDoc)
            root.appendChild(xmlElement)
        }
        xmlDoc.appendChild(root)
        return xmlDoc.firstChild as Element
    }

    /**
     * Parses the Animal Object from the input XML Node.
     *
     * @param node the XML Node from which the Animal Object is to be parsed
     */
    fun createObjectFromXml(node: Node) {
        name = node.attributes.getNamedItem("name").nodeValue
        val childNodes = node.childNodes
        val mutableAnimalsEaten = animalsEaten.toMutableSet()
        val mutablePlantsEaten = plantsEaten.toMutableSet()
        iterateXmlForAnimalAndPlants(childNodes, mutableAnimalsEaten, mutablePlantsEaten)
        animalsEaten = mutableAnimalsEaten
        plantsEaten = mutablePlantsEaten
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("\nAnimal Name = ").append(name)
        if (animalsEaten.isNotEmpty()) {
            sb.append("\n\tAnimals Eaten by ").append(name).append(": ")
        }
        for (animal in animalsEaten) {
            sb.append("\n\t\t").append(animal)
        }
        sb.append("\n")
        if (plantsEaten.isNotEmpty()) {
            sb.append("\n\tPlants Eaten by ").append(name).append(": ")
        }
        for (plant in plantsEaten) {
            sb.append("\n\t\t").append(plant)
        }
        return sb.toString()
    }
}
