import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DoubleBufferTest {

    @Mock
    private Graphics graphics;

    @Mock
    private Dimension dimension;

    private DoubleBuffer doubleBuffer;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
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


    @Ignore("Can't get this work...")
    public void onUpdateVerifyThatTheOffImageIsUpdated() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Field f = doubleBuffer.getClass().getDeclaredField("offGraphics"); //NoSuchFieldException
        f.setAccessible(true);

        Graphics oldOffGraphics = (Graphics) f.get(doubleBuffer);

        Method method = doubleBuffer.getClass().getDeclaredMethod("getOffScreenGraphics", Dimension.class);
        method.setAccessible(true);
        Object r = method.invoke(doubleBuffer, dimension);
        Mockito.when(r).thenReturn(graphics);

        doubleBuffer.update(graphics);

        Field newField = doubleBuffer.getClass().getDeclaredField("offGraphics");
        f.setAccessible(true);
        Graphics newOffGraphics = (Graphics) newField.get(doubleBuffer);

        assertEquals(newOffGraphics, oldOffGraphics);
    }

}