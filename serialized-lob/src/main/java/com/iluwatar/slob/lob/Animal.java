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

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Creates an object Animal with a list of animals and/or plants it consumes.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Animal implements Serializable {

  private String name;
  private Set<Plant> plantsEaten = new HashSet<>();
  private Set<Animal> animalsEaten = new HashSet<>();

  /**
   * Iterates over the input nodes recursively and adds new plants to {@link Animal#plantsEaten} or
   * animals to {@link Animal#animalsEaten} found to input sets respectively.
   *
   * @param childNodes   contains the XML Node containing the Forest
   * @param animalsEaten set of Animals eaten
   * @param plantsEaten  set of Plants eaten
   */
  protected static void iterateXmlForAnimalAndPlants(NodeList childNodes, Set<Animal> animalsEaten,
      Set<Plant> plantsEaten) {
    for (int i = 0; i < childNodes.getLength(); i++) {
      Node child = childNodes.item(i);
      if (child.getNodeType() == Node.ELEMENT_NODE) {
        if (child.getNodeName().equals(Animal.class.getSimpleName())) {
          Animal animalEaten = new Animal();
          animalEaten.createObjectFromXml(child);
          animalsEaten.add(animalEaten);
        } else if (child.getNodeName().equals(Plant.class.getSimpleName())) {
          Plant plant = new Plant();
          plant.createObjectFromXml(child);
          plantsEaten.add(plant);
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
  public Element toXmlElement(Document xmlDoc) {
    Element root = xmlDoc.createElement(Animal.class.getSimpleName());
    root.setAttribute("name", name);
    for (Plant plant : plantsEaten) {
      Element xmlElement = plant.toXmlElement(xmlDoc);
      if (xmlElement != null) {
        root.appendChild(xmlElement);
      }
    }
    for (Animal animal : animalsEaten) {
      Element xmlElement = animal.toXmlElement(xmlDoc);
      if (xmlElement != null) {
        root.appendChild(xmlElement);
      }
    }
    xmlDoc.appendChild(root);
    return (Element) xmlDoc.getFirstChild();
  }

  /**
   * Parses the Animal Object from the input XML Node.
   *
   * @param node the XML Node from which the Animal Object is to be parsed
   */
  public void createObjectFromXml(Node node) {
    name = node.getAttributes().getNamedItem("name").getNodeValue();
    NodeList childNodes = node.getChildNodes();
    iterateXmlForAnimalAndPlants(childNodes, animalsEaten, plantsEaten);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("\nAnimal Name = ").append(name);
    if (!animalsEaten.isEmpty()) {
      sb.append("\n\tAnimals Eaten by ").append(name).append(": ");
    }
    for (Animal animal : animalsEaten) {
      sb.append("\n\t\t").append(animal);
    }
    sb.append("\n");
    if (!plantsEaten.isEmpty()) {
      sb.append("\n\tPlants Eaten by ").append(name).append(": ");
    }
    for (Plant plant : plantsEaten) {
      sb.append("\n\t\t").append(plant);
    }
    return sb.toString();
  }
}
