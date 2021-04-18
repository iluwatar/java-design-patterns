package com.iluwatar.compositeentity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Date: 4/17/21 - 11:45 AM
 *
 * @author zwebrain
 */
public class PersistenceTest {
    final static ConsoleCoarseGrainedObject console = new ConsoleCoarseGrainedObject();

    @Test
    void dependentObjectChangedForPersistenceTest() {
        MessageDependentObject dependentObject = new MessageDependentObject();
        console.dependentObjects[0] = dependentObject;
        String message = "Danger";
        assertNull(console.dependentObjects[0].getData());
        dependentObject.setData(message);
        assertEquals(message, console.dependentObjects[0].getData());
    }

    @Test
    void coarseGrainedObjectChangedForPersistenceTest() {
        MessageDependentObject dependentObject = new MessageDependentObject();
        console.dependentObjects[0] = dependentObject;
        String message = "Danger";
        assertNull(console.dependentObjects[0].getData());
        console.setData(message, "");
        assertEquals(message, dependentObject.getData());
    }
}
