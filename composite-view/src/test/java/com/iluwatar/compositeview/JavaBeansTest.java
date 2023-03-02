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
package com.iluwatar.compositeview;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

class JavaBeansTest {
    @Test
    void testDefaultConstructor() {
        ClientPropertiesBean newBean = new ClientPropertiesBean();
        assertEquals("DEFAULT_NAME", newBean.getName());
        assertTrue(newBean.isBusinessInterest());
        assertTrue(newBean.isScienceNewsInterest());
        assertTrue(newBean.isSportsInterest());
        assertTrue(newBean.isWorldNewsInterest());

    }

    @Test
    void testNameGetterSetter() {
        ClientPropertiesBean newBean = new ClientPropertiesBean();
        assertEquals("DEFAULT_NAME", newBean.getName());
        newBean.setName("TEST_NAME_ONE");
        assertEquals("TEST_NAME_ONE", newBean.getName());
    }

    @Test
    void testBusinessSetterGetter() {
        ClientPropertiesBean newBean = new ClientPropertiesBean();
        assertTrue(newBean.isBusinessInterest());
        newBean.setBusinessInterest(false);
        assertFalse(newBean.isBusinessInterest());
    }

    @Test
    void testScienceSetterGetter() {
        ClientPropertiesBean newBean = new ClientPropertiesBean();
        assertTrue(newBean.isScienceNewsInterest());
        newBean.setScienceNewsInterest(false);
        assertFalse(newBean.isScienceNewsInterest());
    }

    @Test
    void testSportsSetterGetter() {
        ClientPropertiesBean newBean = new ClientPropertiesBean();
        assertTrue(newBean.isSportsInterest());
        newBean.setSportsInterest(false);
        assertFalse(newBean.isSportsInterest());
    }

    @Test
    void testWorldSetterGetter() {
        ClientPropertiesBean newBean = new ClientPropertiesBean();
        assertTrue(newBean.isWorldNewsInterest());
        newBean.setWorldNewsInterest(false);
        assertFalse(newBean.isWorldNewsInterest());
    }

    @Test
    void testRequestConstructor(){
        HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
        ClientPropertiesBean newBean = new ClientPropertiesBean((mockReq));
        assertEquals("DEFAULT_NAME", newBean.getName());
        assertFalse(newBean.isWorldNewsInterest());
        assertFalse(newBean.isBusinessInterest());
        assertFalse(newBean.isScienceNewsInterest());
        assertFalse(newBean.isSportsInterest());
    }
}
