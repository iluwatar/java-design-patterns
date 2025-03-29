package com.iluwatar.publish.subscribe;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.LoggerFactory;

public class LoggerExtension implements BeforeEachCallback, AfterEachCallback {

  private final ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
  private final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

  @Override
  public void afterEach(ExtensionContext extensionContext) throws Exception {
    listAppender.stop();
    listAppender.list.clear();
    logger.detachAppender(listAppender);
  }

  @Override
  public void beforeEach(ExtensionContext extensionContext) throws Exception {
    logger.addAppender(listAppender);
    listAppender.start();
  }

  public List<String> getMessages() {
    return listAppender.list.stream().map(e -> e.getMessage()).collect(Collectors.toList());
  }

  public List<String> getFormattedMessages() {
    return listAppender.list.stream().map(e -> e.getFormattedMessage())
        .collect(Collectors.toList());
  }
}