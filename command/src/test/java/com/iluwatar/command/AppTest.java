package com.iluwatar.command;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AppTest {

	@Test
	public void test_DoingUndoingrRedoingSpells() {
        Wizard wizard = new Wizard();
        Goblin goblin = new Goblin();

        //initial state
        assertEquals(Size.NORMAL, goblin.getSize());
        assertEquals(Visibility.VISIBLE, goblin.getVisibility());

        //do spells
        wizard.castSpell(new ShrinkSpell(), goblin);
        assertEquals(Size.SMALL, goblin.getSize());
        assertEquals(Visibility.VISIBLE, goblin.getVisibility());

        wizard.castSpell(new InvisibilitySpell(), goblin);
        assertEquals(Size.SMALL, goblin.getSize());
        assertEquals(Visibility.INVISIBLE, goblin.getVisibility());

        //now start undoing
        wizard.undoLastSpell();
        assertEquals(Size.SMALL, goblin.getSize());
        assertEquals(Visibility.VISIBLE, goblin.getVisibility());

        wizard.undoLastSpell();
        assertEquals(Size.NORMAL, goblin.getSize());
        assertEquals(Visibility.VISIBLE, goblin.getVisibility());

        //now start redoing
        wizard.redoLastSpell();
        assertEquals(Size.SMALL, goblin.getSize());
        assertEquals(Visibility.VISIBLE, goblin.getVisibility());

        wizard.redoLastSpell();
        assertEquals(Size.SMALL, goblin.getSize());
        assertEquals(Visibility.INVISIBLE, goblin.getVisibility());
	}

    @Test
    public void test_UndoingWhenNotPossible() {
        Wizard wizard = new Wizard();
        Goblin goblin = new Goblin();

        //initial state
        assertEquals(Size.NORMAL, goblin.getSize());
        assertEquals(Visibility.VISIBLE, goblin.getVisibility());

        //undoing when no spell
        wizard.undoLastSpell();

        //who care = same as initial state
        assertEquals(Size.NORMAL, goblin.getSize());
        assertEquals(Visibility.VISIBLE, goblin.getVisibility());

        //do one spell
        wizard.castSpell(new ShrinkSpell(), goblin);
        assertEquals(Size.SMALL, goblin.getSize());
        assertEquals(Visibility.VISIBLE, goblin.getVisibility());

        //undoing back to initial state
        wizard.undoLastSpell();

        //who care = same as initial state
        assertEquals(Size.NORMAL, goblin.getSize());
        assertEquals(Visibility.VISIBLE, goblin.getVisibility());

        //nothing else to undo but who cares - state still same
        wizard.undoLastSpell();
        assertEquals(Size.NORMAL, goblin.getSize());
        assertEquals(Visibility.VISIBLE, goblin.getVisibility());
    }

    @Test
    public void test_RedoingWhenNotPossible() {
        Wizard wizard = new Wizard();
        Goblin goblin = new Goblin();

        //initial state
        assertEquals(Size.NORMAL, goblin.getSize());
        assertEquals(Visibility.VISIBLE, goblin.getVisibility());

        //undoing when no spell
        wizard.redoLastSpell();

        //who care = same as initial state
        assertEquals(Size.NORMAL, goblin.getSize());
        assertEquals(Visibility.VISIBLE, goblin.getVisibility());

        wizard.castSpell(new ShrinkSpell(), goblin);

        //who care = same as initial state
        assertEquals(Size.SMALL, goblin.getSize());
        assertEquals(Visibility.VISIBLE, goblin.getVisibility());

        //redoing same spell
        wizard.redoLastSpell();

        //nothing to do in this case - spell has already been done
        assertEquals(Size.SMALL, goblin.getSize());
        assertEquals(Visibility.VISIBLE, goblin.getVisibility());
    }
}