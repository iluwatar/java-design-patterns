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

// ABOUTME: Data class representing a Plant entity in the forest ecosystem.
// ABOUTME: Supports XML serialization/deserialization for the Serialized LOB pattern.
package com.iluwatar.slob.lob

import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.Serializable
import java.util.StringJoiner

/**
 * Creates an object Plant which contains its name and type.
 */
data class Plant(
    var name: String = "",
    var type: String = ""
) : Serializable {

    /**
     * Provides XML Representation of the Plant.
     *
     * @param xmlDoc to which the XML representation is to be written to
     * @return XML Element contain the Animal representation
     */
    fun toXmlElement(xmlDoc: Document): Element {
        val root = xmlDoc.createElement(Plant::class.simpleName)
        root.setAttribute("name", name)
        root.setAttribute("type", type)
        xmlDoc.appendChild(root)
        return xmlDoc.documentElement
    }

    /**
     * Parses the Plant Object from the input XML Node.
     *
     * @param node the XML Node from which the Animal Object is to be parsed
     */
    fun createObjectFromXml(node: Node) {
        val attributes = node.attributes
        name = attributes.getNamedItem("name").nodeValue
        type = attributes.getNamedItem("type").nodeValue
    }

    override fun toString(): String {
        val stringJoiner = StringJoiner(",")
        stringJoiner.add("Name = $name")
        stringJoiner.add("Type = $type")
        return stringJoiner.toString()
    }
}
