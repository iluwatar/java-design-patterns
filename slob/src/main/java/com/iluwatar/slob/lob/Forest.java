package com.iluwatar.slob.lob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import static com.iluwatar.slob.lob.Animal.iterateXmlForAnimalAndPlants;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Forest implements Serializable {

    private String name;
    private Set<Animal> animals = new HashSet<>();
    private Set<Plant> plants = new HashSet<>();

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

    private Document getXmlDoc() throws ParserConfigurationException {
        return DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder().newDocument();
    }

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
