package com.iluwatar.serialized.lob;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that the department class works correctly
 */
class DepartmentTest {

    @Test
    void testToXmlElement() {
        var department = new Department("department");
        assertNotNull(department.toXmlElement());
    }

    @Test
    void testReadXml() {
        var department = new Department("department");
        var element = department.toXmlElement();
        assertNotNull(Department.readXml(element));
    }

    @Test
    void testAddSubsidiary() {
        var department1 = new Department("department1");
        var department2 = new Department("department2");
        department1.getSubsidiaries().add(department2);
        assertEquals(1, department1.getSubsidiaries().size());
    }

    @Test
    void testGetSubsidiaries() {
        var department1 = new Department("department1");
        assertNotNull(department1.getSubsidiaries());
    }
}