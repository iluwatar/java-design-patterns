package com.iluwatar.event.sourcing.processor;

import com.iluwatar.event.sourcing.event.DomainEvent;
import java.io.File;

/**
 * Base class for Journaling implementations.
 */
public abstract class EventJournal {

  File file;

  /**
   * Write.
   *
   * @param domainEvent the domain event.
   */
  abstract void write(DomainEvent domainEvent);

  /**
   * Reset.
   */
  @SuppressWarnings("ResultOfMethodCallIgnored")
  void reset() {
    file.delete();
  }

  /**
   * Read domain event.
   *
   * @return the domain event.
   */
  abstract DomainEvent readNext();
}
