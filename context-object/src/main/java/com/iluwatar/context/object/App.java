package com.iluwatar.context.object;

import lombok.extern.slf4j.Slf4j;

/**
 * In the context object pattern, information and data from underlying protocol-specific classes/systems is decoupled
 * and stored into a protocol-independent object in an organised format. The pattern ensures the data contained within
 * the context object can be shared and further structured between different layers of a software system.
 *
 * <p> In this example we show how a context object {@link ServiceContext} can be initiated, edited and passed/retrieved
 * in different layers of the program ({@link LayerA}, {@link LayerB}, {@link LayerC}) through use of static methods. </p>
 */
@Slf4j
public class App {

    private static final String SERVICE = "SERVICE";

    /**
     * Program entry point.
     * @param args command line args
     */
    public static void main(String[] args) {
        //Initiate first layer and add service information into context
        var layerA = new LayerA();
        layerA.addAccountInfo(SERVICE);

        LOGGER.info("Context = {}",layerA.getContext());

        //Initiate second layer and preserving information retrieved in first layer through passing context object
        var layerB = new LayerB(layerA);
        layerB.addSessionInfo(SERVICE);

        LOGGER.info("Context = {}",layerB.getContext());

        //Initiate third layer and preserving information retrieved in first and second layer through passing context object
        var layerC = new LayerC(layerB);
        layerC.addSearchInfo(SERVICE);

        LOGGER.info("Context = {}",layerC.getContext());
    }
}
