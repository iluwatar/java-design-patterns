package com.iluwatar.serializedlob;

import java.util.ArrayList;

import org.jdom2.Element;

public class Department {

  /**
   * Private filed name.
   */
  private final String name;

  /**
   * Private filed subsidiaries.
   */
  private final ArrayList<Department> subsidiaries = new ArrayList<>();

  /**
   * Protected constructor.
   *
   * @param name1 name
   */
  protected Department(final String name1) {
    this.name = name1;
  }

  protected static Department readXml(final Element source) {
    var name = source.getAttributeValue("name");
    var result = new Department(name);
    for (Element element : source.getChildren("department")) {
      result.addSubsidiary(readXml(element));
    }
    return result;
  }

  /**
   * Add a department to subsidiaries.
   *
   * @param department the department to be added
   */
  protected void addSubsidiary(final Department department) {
    subsidiaries.add(department);
  }

  /**
   * Convert filed subsidiaries to XmlElement.
   *
   * @return element
   */
  protected Element toXmlElement() {
    var root = new Element("department");
    root.setAttribute("name", name);
    for (Department dep : subsidiaries) {
      root.addContent(dep.toXmlElement());
    }
    return root;
  }

  /**
   * Get method for the field subsidiaries.
   *
   * @return subsidiaries
   */
  protected ArrayList<Department> getSubsidiaries() {
    return subsidiaries;
  }

}
