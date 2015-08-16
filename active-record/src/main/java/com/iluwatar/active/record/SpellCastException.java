package com.iluwatar.active.record;

/**
 * Describes the exception that is raised when a spell can not be cast for some reason.
 *
 * Created by Stephen Lazarionok.
 */
public class SpellCastException extends Exception {

    /**
     * Create an exception with the reason provided.
     * @param message
     */
    public SpellCastException(final String message) {
        super(message);
    }
}
