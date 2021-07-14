/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package domainapp.dom.modules.simple;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import com.google.common.collect.Lists;
import org.apache.isis.applib.DomainObjectContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Test for SimpleObjects
 */
public class SimpleObjectsTest {

    DomainObjectContainer mockContainer;

    SimpleObjects simpleObjects;

    @BeforeEach
    public void setUp() {
        mockContainer = Mockito.mock(DomainObjectContainer.class);

        simpleObjects = new SimpleObjects();
        simpleObjects.container = mockContainer;
    }

    @Test
    public void testCreate() {

        // given
        final SimpleObject simpleObject = new SimpleObject();
        // and
        when(mockContainer.newTransientInstance(SimpleObject.class)).thenReturn(simpleObject);

        // when
        String objectName = "Foobar";
        final SimpleObject obj = simpleObjects.create(objectName);

        // then
        assertEquals(simpleObject, obj);
        assertEquals(objectName, obj.getName());
    }

    @Test
    public void testListAll() {

        // given
        final List<SimpleObject> all = Lists.newArrayList();
        // and
        when(mockContainer.allInstances(SimpleObject.class)).thenReturn(all);

        // when
        final List<SimpleObject> list = simpleObjects.listAll();

        // then
        assertEquals(all, list);
    }

}
