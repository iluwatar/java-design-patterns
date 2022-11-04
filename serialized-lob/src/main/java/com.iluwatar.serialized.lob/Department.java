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