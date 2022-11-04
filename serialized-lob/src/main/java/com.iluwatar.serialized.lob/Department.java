package com.iluwatar.serialized.lob;

import java.util.ArrayList;
import org.jdom2.Element;

public class Department {
    /** Static constants */
    public static final String XML_ELEMENT_NAME = "name";
    public static final String XML_ELEMENT_DEPARTMENT = "department";

    /** Department fields */
    private final String name;
    private final ArrayList<Department> subsidiaries = new ArrayList<>();

    /**
     * Protected constructor
     *
     * @param name department name
     */
    protected Department(final String name) {
        this.name = name;
    }

    /**
     * Get method for the subsidiaries field
     *
     * @return List of the department's subsidiaries
     */
    protected ArrayList<Department> getSubsidiaries() {
        return subsidiaries;
    }

    /**
     * Deserialize an XML element to department
     *
     * @param source element source
     * @return department object of XML element
     */
    protected static Department readXml(final Element source) {
        // Deserialize source department
        var name = source.getAttributeValue(XML_ELEMENT_NAME);
        var result = new Department(name);
        // Deserialize subsidiary departments
        for (Element element : source.getChildren(XML_ELEMENT_DEPARTMENT))
            result.subsidiaries.add(readXml(element));
        return result;
    }

    /**
     * Serialize this department object to an XML element
     *
     * @return XML element
     */
    protected Element toXmlElement() {
        // Serialize the root department
        var root = new Element(XML_ELEMENT_DEPARTMENT);
        root.setAttribute(XML_ELEMENT_NAME, name);
        // Serialize subsidiary departments
        for (Department dep : subsidiaries)
            root.addContent(dep.toXmlElement());
        return root;
    }
}