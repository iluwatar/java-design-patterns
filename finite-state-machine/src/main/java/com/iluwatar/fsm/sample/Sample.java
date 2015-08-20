package com.iluwatar.fsm.sample;

import com.iluwatar.fsm.FiniteStateMachine;
import com.iluwatar.fsm.TransitionHandler;

import java.util.HashMap;
import java.util.Map;


/**
 * A demo application that shows how to work with traffic lights.
 * <p/>
 * Created by Stephen Lazarionok.
 */
public class Sample {

    public static void main(final String[] args) {

        // Build a simple context based on map
        final Map<String, Object> context = new HashMap<String, Object>();
        context.put("mycar", new Car());

        // A handler that just logs a transition
        final TransitionHandler<Map<String, Object>> logTransitionHandler = new TransitionHandler<Map<String, Object>>() {

            @Override
            public void handle(Enum<?> from, Enum<?> to, Map<String, Object> context) {
                System.out.println("Performing transition from '" + from + "' to '" + to + "'...");
            }
        };

        // A handler that starts driving my car
        final TransitionHandler<Map<String, Object>> startDrivingHandler = new TransitionHandler<Map<String, Object>>() {

            @Override
            public void handle(Enum<?> from, Enum<?> to, Map<String, Object> context) {

                Car.class.cast(context.get("mycar")).drive();
            }
        };

        final FiniteStateMachine<Map<String, ?>> trafficLightFsm = FiniteStateMachine.Builder.create()
                .withDefaultState(TrafficLightState.RED)
                .addTransition(TrafficLightState.GREEN, TrafficLightEvent.SWITCH, TrafficLightState.YELLOW, logTransitionHandler)
                .addTransition(TrafficLightState.YELLOW, TrafficLightEvent.SWITCH, TrafficLightState.RED, logTransitionHandler)
                .addTransition(TrafficLightState.RED, TrafficLightEvent.SWITCH, TrafficLightState.YELLOW, logTransitionHandler)
                .addTransition(TrafficLightState.YELLOW, TrafficLightEvent.SWITCH, TrafficLightState.GREEN, startDrivingHandler)
                .withContext(context)
                .build();

        trafficLightFsm.raiseEvent(TrafficLightEvent.SWITCH);
        trafficLightFsm.raiseEvent(TrafficLightEvent.SWITCH);

    }

}
