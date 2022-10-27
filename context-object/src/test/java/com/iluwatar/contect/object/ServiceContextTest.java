package com.iluwatar.contect.object;

import com.iluwatar.context.object.LayerA;
import com.iluwatar.context.object.LayerB;
import com.iluwatar.context.object.LayerC;
import com.iluwatar.context.object.ServiceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Date: 10/24/2022 - 3:18
 *
 * @author Chak Chan
 */
public class ServiceContextTest {

    private static final String SERVICE = "SERVICE";

    private LayerA layerA;

    @BeforeEach
    void initiateLayerA() {
        this.layerA = new LayerA();
    }

    @Test
    void testSameContextPassedBetweenLayers() {
        ServiceContext context1 = layerA.getContext();
        LayerB layerB = new LayerB(layerA);
        ServiceContext context2 = layerB.getContext();
        LayerC layerC = new LayerC(layerB);
        ServiceContext context3 = layerC.getContext();

        assertSame(context1, context2);
        assertSame(context2, context3);
        assertSame(context3, context1);
    }

    @Test
    void testScopedDataPassedBetweenLayers() {
        LayerA layerA = new LayerA();
        layerA.addAccountInfo(SERVICE);
        LayerB layerB = new LayerB(layerA);
        LayerC layerC = new LayerC(layerB);
        layerC.addSearchInfo(SERVICE);
        ServiceContext context = layerC.getContext();

        assertEquals(SERVICE,context.getACCOUNT_SERVICE());
        assertNull(context.getSESSION_SERVICE());
        assertEquals(SERVICE,context.getSEARCH_SERVICE());
    }

    @Test
    void testToString() {
        assertEquals(layerA.getContext().toString(),"null null null");
        layerA.addAccountInfo(SERVICE);
        assertEquals(layerA.getContext().toString(), "SERVICE null null");
        LayerB layerB = new LayerB(layerA);
        layerB.addSessionInfo(SERVICE);
        LayerC layerC = new LayerC(layerB);
        assertEquals(layerC.getContext().toString(), "SERVICE SERVICE null");
    }
}
