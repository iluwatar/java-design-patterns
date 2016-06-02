package com.iluwatar.abstractdocument;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class AbstractDocumentTest {

    private static final String KEY = "key";
    private static final Object VALUE = "value";

    private class DocumentImplementation extends AbstractDocument {

        DocumentImplementation(Map<String, Object> properties) {
            super(properties);
        }
    }

    private DocumentImplementation document = new DocumentImplementation(new HashMap<>());

    @Test
    public void shouldPutAndGetValue() {
        document.put(KEY, VALUE);
        assertEquals(VALUE, document.get(KEY));
        System.out.println(document);
    }

    @Test
    public void shouldRetrieveChildren() {
        Map<String,Object> child1 = new HashMap<>();
        Map<String,Object> child2 = new HashMap<>();
        List<Map<String, Object>> children = Arrays.asList(child1, child2);

        document.put(KEY, children);

        Stream<DocumentImplementation> childrenStream = document.children(KEY, DocumentImplementation::new);
        assertNotNull(children);
        assertEquals(2, childrenStream.count());
    }

}
