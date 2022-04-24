package com.iluwater.component;

import static junit.framework.TestCase.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.iluwater.component.component.GraphicComponent;
import com.iluwater.component.component.HealthComponent;
import com.iluwater.component.component.PhysicComponent;
import com.iluwater.component.model.Component;
import com.iluwater.component.model.Vector2d;

public class GameObjectTest {
    
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() throws UnsupportedEncodingException {
        System.setOut(new PrintStream(outputStream, true, "UTF-8"));
    }

    @Test
    void gameObject_Constructor_Test() throws InterruptedException, UnsupportedEncodingException {
        final GameObject gameObject1 = new GameObject("Hero", 35f, new Vector2d(3.0f, 4.5f));
        gameObject1.addComponent(new GraphicComponent());
        gameObject1.addComponent(new HealthComponent());
        gameObject1.addComponent(new PhysicComponent());

        // Validation
        assertEquals("Hero", gameObject1.getName());
        assertEquals(35f, gameObject1.getHealth());
        assertEquals(3.0f, gameObject1.getPosition().getX());
        assertEquals(4.5f, gameObject1.getPosition().getY());
        assertEquals("Vector2d[3.00, 4.50]", gameObject1.getPosition().toString());
    }

    @Test
    void gameObject_getComponent_Test() throws InterruptedException, UnsupportedEncodingException {
        final GameObject gameObject1 = new GameObject("Hero", 35f, new Vector2d(3.0f, 4.5f));
        gameObject1.addComponent(new GraphicComponent());
        gameObject1.addComponent(new HealthComponent());
        gameObject1.addComponent(new PhysicComponent());

        // Validation
        final Component healthComponent = gameObject1.getComponent(HealthComponent.class);
        assertNotNull(healthComponent);
        assertEquals("Health Component", healthComponent.getName());
        final Component graphicComponent = gameObject1.getComponent(GraphicComponent.class);
        assertNotNull(graphicComponent);
        assertEquals("Graphic Component", graphicComponent.getName());
        final Component physicComponent = gameObject1.getComponent(PhysicComponent.class);
        assertNotNull(physicComponent);
        assertEquals("Physic Component", physicComponent.getName());
    }

    @Test
    void gameObject_Update_Test() throws InterruptedException, UnsupportedEncodingException {
        final GameObject gameObject1 = new GameObject("Hero", 35f, new Vector2d(3.0f, 4.5f));
        gameObject1.addComponent(new GraphicComponent());
        gameObject1.addComponent(new HealthComponent());
        gameObject1.addComponent(new PhysicComponent());

        // Validation
        gameObject1.update();
        final String val = outputStream.toString("utf-8");
        String expected = "GraphicComponent - Hero shows normal graphic\n" +
                "HealthComponent - Hero can still take some hit [35.00]\n" +
                "PhysicComponent - Hero is in position Vector2d[3.00, 4.50]\n";
        expected = expected.replaceAll("\\n|\\r\\n", System.getProperty("line.separator"));
        assertEquals(expected, val);
    }

    @Test
    void gameObject_ComponentNotfound_Test() throws InterruptedException, UnsupportedEncodingException {
        final GameObject gameObject1 = new GameObject("Hero", 35f, new Vector2d(3.0f, 4.5f));
        gameObject1.addComponent(new GraphicComponent());
        gameObject1.addComponent(new HealthComponent());
        Component physicComponent = gameObject1.getComponent(PhysicComponent.class);
        assertNull(physicComponent);
    }
}
