package com.iluwatar.queue.load.leveling;

import lombok.extern.slf4j.Slf4j;

/**
 * ServiceExecutor class. This class retrieves and processes messages from a queue.
 */
@Slf4j
public class ServiceExecutor implements Runnable {

  private final MessageQueue messageQueue;
  private final MessageProcessor messageProcessor;
  private final long processingDelay;

  /**
   * Constructor for ServiceExecutor.
   *
   * @param messageQueue     the queue to retrieve messages from.
   * @param messageProcessor the processor responsible for processing messages.
   * @param processingDelay  the delay (in milliseconds) between processing messages.
   */
  public ServiceExecutor(MessageQueue messageQueue, MessageProcessor messageProcessor, long processingDelay) {
    this.messageQueue = messageQueue;
    this.messageProcessor = messageProcessor;
    this.processingDelay = processingDelay;
  }

  /**
   * The ServiceExecutor thread will retrieve each message and process it.
   */
  @Override
  public void run() {
    try {
      while (!Thread.currentThread().isInterrupted()) {
        var message = messageQueue.retrieveMsg();

        if (message != null) {
          messageProcessor.process(message); // Delegates processing logic to the processor
          LOGGER.info("{} has been processed successfully.", message);
        } else {
          LOGGER.info("Service Executor: No messages available. Waiting...");
        }

        Thread.sleep(processingDelay);
      }
    } catch (InterruptedException e) {
      LOGGER.warn("ServiceExecutor thread interrupted. Exiting gracefully...");
      Thread.currentThread().interrupt(); // Restore interrupted status
    } catch (Exception e) {
      LOGGER.error("An error occurred while processing the message: {}", e.getMessage(), e);
    } finally {
      LOGGER.info("ServiceExecutor has stopped.");
    }
  }

  /**
   * MessageProcessor interface defines the processing logic.
   */
  @FunctionalInterface
  public interface MessageProcessor {
    void process(Message message) throws Exception;
  }
}
