package com.iluwatar.serializedlob;

import org.jdom2.Element;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

  @Test
  void testToXmlElement() {
    var department = new Department("department1");
    assertNotNull(department.toXmlElement());
  }

  @Test
  void testReadXml() {
    var department = new Department("department1");
    var element = department.toXmlElement();
    assertNotNull(Department.readXml(element));
  }

  @Test
  void testAddSubsidiary() {
    var department1 = new Department("department1");
    var department2 = new Department("department2");
    department1.addSubsidiary(department2);
    assertEquals(1, department1.getSubsidiaries().size());
  }

  @Test
  void testGetSubsidiaries() {
    var department1 = new Department("department1");
    assertNotNull(department1.getSubsidiaries());
  }

}
