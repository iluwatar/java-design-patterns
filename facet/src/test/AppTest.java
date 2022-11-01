package test;

import main.Facet;
import main.defaultContext;
import main.defaultSentry;
import main.securityMethods;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppTest {
    @Test
    public void testCreate() throws Exception {
        Facet defaultFacet = Facet.create();
        assertNotNull(defaultFacet);
        Facet facet = Facet.create(new defaultSentry(new defaultContext()), new Class[0]);
        assertNotNull(facet);
    }

    @Test
    public void testQuery() throws Exception {
        Facet facet = Facet.create(new defaultSentry(new defaultContext()), new Class[0]);
        Class<securityMethods> securityMethodsClass = null;
        Class[] classes = {securityMethodsClass};
        Class[] query = Facet.query(facet,classes);
        assertArrayEquals(query,classes);
    }

    @Test
    public void testNarrow() throws Exception {
        Facet facet = Facet.create(new defaultSentry(new defaultContext()), new Class[0]);
        Class<securityMethods> securityMethodsClass = null;
        Class[] classes = {securityMethodsClass};
        Facet.narrow(facet,classes);
        assertEquals(classes.getClass(),Facet.query(facet,classes)[0]);

    }
}