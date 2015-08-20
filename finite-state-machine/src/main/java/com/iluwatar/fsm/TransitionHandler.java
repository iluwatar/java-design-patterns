package com.iluwatar.fsm;

/**
 * Represents a hook that handles transitions from one to another state.
 *
 * Created by Stephen Lazarionok.
 */
public interface TransitionHandler<C> {

    /**
     * Handles a transition from one to another state.
     *
     * @param from - an initial state
     * @param to - a target state
     * @param context - a custom context passed.
     */
    void handle(Enum<?> from, Enum<?> to, C context);
}
