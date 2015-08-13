package com.iluwatar.fsm.examples.trafficlight;

import com.iluwatar.fsm.FiniteStateMachine;
import com.iluwatar.fsm.TransitionHandler;

/**
 * Created by Stephen Lazarionok.
 */
public class TrafficLightApp {

    public static void main(final String[] args) {

        final TransitionHandler logTransitionHandler = new TransitionHandler() {
            @Override
            public void perform(Enum from, Enum to, Object context) {
                System.out.println("Performing transition from '" + from + "' to '" + to + "'...");
            }
        };

        final FiniteStateMachine trafficLightFsm = FiniteStateMachine.Builder.create()
                .withDefaultState(TrafficLightState.GREEN)
                .addTransition(TrafficLightState.GREEN, TrafficLightEvent.SWITCH, TrafficLightState.YELLOW, logTransitionHandler)
                .addTransition(TrafficLightState.YELLOW, TrafficLightEvent.SWITCH, TrafficLightState.RED, logTransitionHandler)
                .addTransition(TrafficLightState.RED, TrafficLightEvent.SWITCH, TrafficLightState.YELLOW, logTransitionHandler)
                .addTransition(TrafficLightState.YELLOW, TrafficLightEvent.SWITCH, TrafficLightState.GREEN, logTransitionHandler)
                .build();

        trafficLightFsm.raiseEvent(TrafficLightEvent.SWITCH);
        trafficLightFsm.raiseEvent(TrafficLightEvent.SWITCH);

    }
}
