import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DoubleBufferTest {

    private DoubleBuffer doubleBuffer;

    @Before
    public void setUp() throws Exception {
        this.doubleBuffer = new DoubleBuffer();
    }

    @After
    public void tearDown() throws Exception {
        this.doubleBuffer = null;
    }

    @Test
    public void onMouseClickTheShowMustNotGoOnAfterMouseClick() throws NoSuchFieldException, IllegalAccessException {
        doubleBuffer.init();
        MouseEvent me = new MouseEvent(doubleBuffer, 0, 0, 0, 100, 100, 1, false);

        MouseListener[] listeners = doubleBuffer.getMouseListeners();
        listeners[0].mousePressed(me);

        Field f = doubleBuffer.getClass().getDeclaredField("frozen"); //NoSuchFieldException
        f.setAccessible(true);
        assertTrue((boolean) f.get(doubleBuffer)); //IllegalAccessException
    }

    @Test
    public void onStartANewThreadShouldStart() throws NoSuchFieldException, IllegalAccessException {
        doubleBuffer.start();

        Field f = doubleBuffer.getClass().getDeclaredField("animatorThread"); //NoSuchFieldException
        f.setAccessible(true);

        Thread animatorThread = (Thread) f.get(doubleBuffer);
        assertEquals(Thread.State.RUNNABLE, animatorThread.getState());
    }

}