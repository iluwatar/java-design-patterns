package com.illuwatar.compositeview;

import com.iluwatar.compositeview.ClientPropertiesBean;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class JavaBeansTest {
    @Test
    public void testDefaultConstructor() {
        ClientPropertiesBean newBean = new ClientPropertiesBean();
        assertEquals("DEFAULT_NAME", newBean.getName());
        assertTrue(newBean.isBusinessInterest());
        assertTrue(newBean.isScienceNewsInterest());
        assertTrue(newBean.isSportsInterest());
        assertTrue(newBean.isWorldNewsInterest());

    }

    @Test
    public void testNameGetterSetter() {
        ClientPropertiesBean newBean = new ClientPropertiesBean();
        assertEquals("DEFAULT_NAME", newBean.getName());
        newBean.setName("TEST_NAME_ONE");
        assertEquals("TEST_NAME_ONE", newBean.getName());
    }

    @Test
    public void testBusinessSetterGetter() {
        ClientPropertiesBean newBean = new ClientPropertiesBean();
        assertTrue(newBean.isBusinessInterest());
        newBean.setBusinessInterest(false);
        assertFalse(newBean.isBusinessInterest());
    }

    @Test
    public void testScienceSetterGetter() {
        ClientPropertiesBean newBean = new ClientPropertiesBean();
        assertTrue(newBean.isScienceNewsInterest());
        newBean.setScienceNewsInterest(false);
        assertFalse(newBean.isScienceNewsInterest());
    }

    @Test
    public void testSportsSetterGetter() {
        ClientPropertiesBean newBean = new ClientPropertiesBean();
        assertTrue(newBean.isSportsInterest());
        newBean.setSportsInterest(false);
        assertFalse(newBean.isSportsInterest());
    }

    @Test
    public void testWorldSetterGetter() {
        ClientPropertiesBean newBean = new ClientPropertiesBean();
        assertTrue(newBean.isWorldNewsInterest());
        newBean.setWorldNewsInterest(false);
        assertFalse(newBean.isWorldNewsInterest());
    }
}
