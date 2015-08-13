package com.iluwatar.fsm;

/**
 * Created by Stephen Lazarionok.
 */
public interface TransitionHandler<C> {

    void perform(Enum<?> from, Enum<?> to, C context);
}
