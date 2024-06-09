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
package com.iluwatar.slob.lob;

import static com.iluwatar.slob.lob.Animal.iterateXmlForAnimalAndPlants;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Creates an object Forest which contains animals and plants as its constituents. Animals may eat
 * plants or other animals in the forest.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Forest implements Serializable {

  private String name;
  private Set<Animal> animals = new HashSet<>();
  private Set<Plant> plants = new HashSet<>();

  /**
   * Provides the representation of Forest in XML form.
   *
   * @return XML Element
   */
  public Element toXmlElement() throws ParserConfigurationException {
    Document xmlDoc = getXmlDoc();

    Element forestXml = xmlDoc.createElement("Forest");
    forestXml.setAttribute("name", name);

    Element animalsXml = xmlDoc.createElement("Animals");
    for (Animal animal : animals) {
      Element animalXml = animal.toXmlElement(xmlDoc);
      animalsXml.appendChild(animalXml);
    }
    forestXml.appendChild(animalsXml);

    Element plantsXml = xmlDoc.createElement("Plants");
    for (Plant plant : plants) {
      Element plantXml = plant.toXmlElement(xmlDoc);
      plantsXml.appendChild(plantXml);
    }
    forestXml.appendChild(plantsXml);
    return forestXml;
  }

  /**
   * Returns XMLDoc to use for XML creation.
   *
   * @return XML DOC Object
   * @throws ParserConfigurationException {@inheritDoc}
   */
  private Document getXmlDoc() throws ParserConfigurationException {
    return DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder().newDocument();
  }

  /**
   * Parses the Forest Object from the input XML Document.
   *
   * @param document the XML document from which the Forest is to be parsed
   */
  public void createObjectFromXml(Document document) {
    name = document.getDocumentElement().getAttribute("name");
    NodeList nodeList = document.getElementsByTagName("*");
    iterateXmlForAnimalAndPlants(nodeList, animals, plants);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("\n");
    sb.append("Forest Name = ").append(name).append("\n");
    sb.append("Animals found in the ").append(name).append(" Forest: \n");
    for (Animal animal : animals) {
      sb.append("\n--------------------------\n");
      sb.append(animal.toString());
      sb.append("\n--------------------------\n");
    }
    sb.append("\n");
    sb.append("Plants in the ").append(name).append(" Forest: \n");
    for (Plant plant : plants) {
      sb.append("\n--------------------------\n");
      sb.append(plant.toString());
      sb.append("\n--------------------------\n");
    }
    return sb.toString();
  }
}
