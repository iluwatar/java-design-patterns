package com.iluwatar.servicetoworker;

import com.iluwatar.model.view.controller.Fatigue;
import com.iluwatar.model.view.controller.Health;
import com.iluwatar.model.view.controller.Nourishment;

/**
 * The type Command.
 */

/**
 * Instantiates a new Command.
 *
 * @param fatigue     the fatigue
 * @param health      the health
 * @param nourishment the nourishment
 */
public record Command(Fatigue fatigue,Health health,Nourishment nourishment)
{
}
