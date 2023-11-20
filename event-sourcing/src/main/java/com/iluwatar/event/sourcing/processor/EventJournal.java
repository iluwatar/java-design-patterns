package com.iluwatar.event.sourcing.processor;

import com.iluwatar.event.sourcing.event.DomainEvent;
import java.io.File;
import lombok.extern.slf4j.Slf4j;

/**
 * Base class for Journaling implementations.
 */
@Slf4j
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
  void reset() {
    if (file.delete()) {
      LOGGER.info("File cleared successfully............");
    }
  }

  /**
   * Read domain event.
   *
   * @return the domain event.
   */
  abstract DomainEvent readNext();
}
