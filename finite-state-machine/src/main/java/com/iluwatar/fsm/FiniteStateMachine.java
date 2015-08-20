package com.iluwatar.fsm;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a simple finite state machine implementation with a custom context and where events and states are defined as enumerations.
 *
 * Created by Stephen Lazarionok.
 */
public final class FiniteStateMachine<C> {

    private Enum<?> state;

    private C context;

    private Map<TransitionMappingKey, TransitionMappingValue> transitions;

    public Enum<?> getState() {
        return state;
    }

    public void raiseEvent(Enum<?> event) {

        System.out.println("Event '" + event + "' has been raised...");

        // Tries to find the transition mapping by the start state and the event raised.
        final TransitionMappingValue transitionMappingValue = transitions.get(new TransitionMappingKey(event, state));
        if (transitionMappingValue != null) {

            // Gets the target state
            final Enum<?> targetState = transitionMappingValue.getState();

            // Invokes the transition handler if exists
            if (transitionMappingValue.getTransitionHandler() != null) {
                transitionMappingValue.getTransitionHandler().handle(state, targetState, context);
            }

            final Enum<?> oldState = this.state;
            this.state = targetState;
            System.out.println("Transition from '" + oldState + "' to '" + state + "' triggered by '"+ event + "' event has been completed.");
        }
        else {
            System.out.println("No available transitions found by event '" + event + "' from state '" + state + "'.");
        }
    }

    private static final class TransitionMappingKey {

        private Enum<?> event;

        private Enum<?> state;

        private TransitionMappingKey(Enum<?> event, Enum<?> state) {
            this.event = event;
            this.state = state;
        }

        public Enum<?> getEvent() {
            return event;
        }

        public Enum<?> getState() {
            return state;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TransitionMappingKey that = (TransitionMappingKey) o;

            if (event != null ? !event.equals(that.event) : that.event != null) return false;
            if (state != null ? !state.equals(that.state) : that.state != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = event != null ? event.hashCode() : 0;
            result = 31 * result + (state != null ? state.hashCode() : 0);
            return result;
        }
    }

    private static final class TransitionMappingValue<C> {

        private TransitionHandler<C> transitionHandler;

        private Enum<?> state;

        public TransitionMappingValue(TransitionHandler<C> transitionHandler, Enum<?> state) {
            this.transitionHandler = transitionHandler;
            this.state = state;
        }

        public TransitionHandler<C> getTransitionHandler() {
            return transitionHandler;
        }

        public Enum<?> getState() {
            return state;
        }
    }

    private FiniteStateMachine() {
    }

    /**
     * A builder for custom finite state machines.
     *
     */
    public static final class Builder<C> {


        private Enum<?> state;

        private C context;

        private Map<TransitionMappingKey, TransitionMappingValue> transitions = new HashMap<TransitionMappingKey, TransitionMappingValue>();

        private Builder() {
        }

        /**
         * Creates a new builder.
         * @return a new builder instance.
         */
        public static Builder create() {
            return new Builder();
        }

        /**
         * Defines an initial state.
         * @param state
         * @return
         */
        public Builder withDefaultState(final Enum<?> state) {
            this.state = state;
            return this;
        }

        /**
         * Add a transition.
         * @param from - 'from' state
         * @param event - an event that triggers the transition
         * @param to - 'to' state
         * @param transitionHandler - a transition handler.
         * @return
         */
        public Builder addTransition(final Enum<?> from, final Enum<?> event, final Enum<?> to, final TransitionHandler<C> transitionHandler) {
            transitions.put(new TransitionMappingKey(event, from), new TransitionMappingValue(transitionHandler, to));
            return this;
        }

        /**
         * Defines the context of the FSM. Might be used to pass external services to handle transitions within the FSM.
         * @param context
         * @return
         */
        public Builder withContext(final C context) {
            this.context = context;
            return this;
        }

        /**
         * Builds a FSM based on the rules defined.
         * @return
         */
        public FiniteStateMachine<C> build() {
            final FiniteStateMachine<C> result = new FiniteStateMachine<C>();
            result.context = context;
            result.transitions = transitions;
            result.state = state;
            return result;
        }
    }

}
