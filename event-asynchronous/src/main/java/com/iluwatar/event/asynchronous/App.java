/**
 * The MIT License Copyright (c) 2014-2016 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.iluwatar.event.asynchronous;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

/**
 *
 * This application demonstrates the <b>Event-based Asynchronous</b> pattern. Essentially, users (of the pattern) may
 * choose to run events in an Asynchronous or Synchronous mode. There can be multiple Asynchronous events running at
 * once but only one Synchronous event can run at a time. Asynchronous events are synonymous to multi-threads. The key
 * point here is that the threads run in the background and the user is free to carry on with other processes. Once an
 * event is complete, the appropriate listener/callback method will be called. The listener then proceeds to carry out
 * further processing depending on the needs of the user.
 *
 * The {@link EventManager} manages the events/threads that the user creates. Currently, the supported event operations
 * are: <code>start</code>, <code>stop</code>, <code>getStatus</code>. For Synchronous events, the user is unable to
 * start another (Synchronous) event if one is already running at the time. The running event would have to either be
 * stopped or completed before a new event can be started.
 *
 * The Event-based Asynchronous Pattern makes available the advantages of multithreaded applications while hiding many
 * of the complex issues inherent in multithreaded design. Using a class that supports this pattern can allow you to:-
 * (1) Perform time-consuming tasks, such as downloads and database operations, "in the background," without
 * interrupting your application. (2) Execute multiple operations simultaneously, receiving notifications when each
 * completes. (3) Wait for resources to become available without stopping ("hanging") your application. (4) Communicate
 * with pending asynchronous operations using the familiar events-and-delegates model.
 *
 * @see EventManager
 * @see Event
 *
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  public static final String PROP_FILE_NAME = "config.properties";

  boolean interactiveMode = false;

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    App app = new App();

    app.setUp();
    app.run();
  }

  /**
   * App can run in interactive mode or not. Interactive mode == Allow user interaction with command line.
   * Non-interactive is a quick sequential run through the available {@link EventManager} operations.
   */
  public void setUp() {
    Properties prop = new Properties();

    InputStream inputStream = App.class.getClassLoader().getResourceAsStream(PROP_FILE_NAME);

    if (inputStream != null) {
      try {
        prop.load(inputStream);
      } catch (IOException e) {
        LOGGER.error("{} was not found. Defaulting to non-interactive mode.", PROP_FILE_NAME, e);
      }
      String property = prop.getProperty("INTERACTIVE_MODE");
      if (property.equalsIgnoreCase("YES")) {
        interactiveMode = true;
      }
    }
  }

  /**
   * Run program in either interactive mode or not.
   */
  public void run() {
    if (interactiveMode) {
      runInteractiveMode();
    } else {
      quickRun();
    }
  }

  /**
   * Run program in non-interactive mode.
   */
  public void quickRun() {
    EventManager eventManager = new EventManager();

    try {
      // Create an Asynchronous event.
      int aEventId = eventManager.createAsync(60);
      LOGGER.info("Async Event [{}] has been created.", aEventId);
      eventManager.start(aEventId);
      LOGGER.info("Async Event [{}] has been started.", aEventId);

      // Create a Synchronous event.
      int sEventId = eventManager.create(60);
      LOGGER.info("Sync Event [{}] has been created.", sEventId);
      eventManager.start(sEventId);
      LOGGER.info("Sync Event [{}] has been started.", sEventId);

      eventManager.status(aEventId);
      eventManager.status(sEventId);

      eventManager.cancel(aEventId);
      LOGGER.info("Async Event [{}] has been stopped.", aEventId);
      eventManager.cancel(sEventId);
      LOGGER.info("Sync Event [{}] has been stopped.", sEventId);

    } catch (MaxNumOfEventsAllowedException | LongRunningEventException | EventDoesNotExistException
        | InvalidOperationException e) {
      LOGGER.error(e.getMessage());
    }
  }

  /**
   * Run program in interactive mode.
   */
  public void runInteractiveMode() {
    EventManager eventManager = new EventManager();

    Scanner s = new Scanner(System.in);
    int option = -1;
    while (option != 4) {
      LOGGER.info("Hello. Would you like to boil some eggs?");
      LOGGER.info("(1) BOIL AN EGG \n(2) STOP BOILING THIS EGG \n(3) HOW ARE MY EGGS? \n(4) EXIT");
      LOGGER.info("Choose [1,2,3,4]: ");
      option = s.nextInt();

      if (option == 1) {
        s.nextLine();
        LOGGER.info("Boil multiple eggs at once (A) or boil them one-by-one (S)?: ");
        String eventType = s.nextLine();
        LOGGER.info("How long should this egg be boiled for (in seconds)?: ");
        int eventTime = s.nextInt();
        if (eventType.equalsIgnoreCase("A")) {
          try {
            int eventId = eventManager.createAsync(eventTime);
            eventManager.start(eventId);
            LOGGER.info("Egg [{}] is being boiled.", eventId);
          } catch (MaxNumOfEventsAllowedException | LongRunningEventException | EventDoesNotExistException e) {
            LOGGER.error(e.getMessage());
          }
        } else if (eventType.equalsIgnoreCase("S")) {
          try {
            int eventId = eventManager.create(eventTime);
            eventManager.start(eventId);
            LOGGER.info("Egg [{}] is being boiled.", eventId);
          } catch (MaxNumOfEventsAllowedException | InvalidOperationException | LongRunningEventException
              | EventDoesNotExistException e) {
            LOGGER.error(e.getMessage());
          }
        } else {
          LOGGER.info("Unknown event type.");
        }
      } else if (option == 2) {
        LOGGER.info("Which egg?: ");
        int eventId = s.nextInt();
        try {
          eventManager.cancel(eventId);
          LOGGER.info("Egg [{}] is removed from boiler.", eventId);
        } catch (EventDoesNotExistException e) {
          LOGGER.error(e.getMessage());
        }
      } else if (option == 3) {
        s.nextLine();
        LOGGER.info("Just one egg (O) OR all of them (A) ?: ");
        String eggChoice = s.nextLine();

        if (eggChoice.equalsIgnoreCase("O")) {
          LOGGER.info("Which egg?: ");
          int eventId = s.nextInt();
          try {
            eventManager.status(eventId);
          } catch (EventDoesNotExistException e) {
            LOGGER.error(e.getMessage());
          }
        } else if (eggChoice.equalsIgnoreCase("A")) {
          eventManager.statusOfAllEvents();
        }
      } else if (option == 4) {
        eventManager.shutdown();
      }
    }

    s.close();
  }

}
