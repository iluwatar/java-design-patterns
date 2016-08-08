/**
 * The MIT License Copyright (c) 2014 Ilkka Seppälä
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

  boolean interactiveMode = false;

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
    String propFileName = "config.properties";

    InputStream inputStream = App.class.getClassLoader().getResourceAsStream(propFileName);

    if (inputStream != null) {
      try {
        prop.load(inputStream);
      } catch (IOException e) {
      }
      String property = prop.getProperty("INTERACTIVE_MODE");
      if (property.equalsIgnoreCase("YES")) {
        interactiveMode = true;
      }
    }
  }

  public void run() {
    if (interactiveMode) {
      runInteractiveMode();
    } else {
      quickRun();
    }
  }

  public void quickRun() {
    EventManager eventManager = new EventManager();

    try {
      // Create an Asynchronous event.
      int aEventID = eventManager.createAsyncEvent(60);
      System.out.println("Event [" + aEventID + "] has been created.");
      eventManager.startEvent(aEventID);
      System.out.println("Event [" + aEventID + "] has been started.");

      // Create a Synchronous event.
      int sEventID = eventManager.createSyncEvent(60);
      System.out.println("Event [" + sEventID + "] has been created.");
      eventManager.startEvent(sEventID);
      System.out.println("Event [" + sEventID + "] has been started.");

      eventManager.getStatus(aEventID);
      eventManager.getStatus(sEventID);

      eventManager.stopEvent(aEventID);
      System.out.println("Event [" + aEventID + "] has been stopped.");
      eventManager.stopEvent(sEventID);
      System.out.println("Event [" + sEventID + "] has been stopped.");

    } catch (MaxNumOfEventsAllowedException | LongRunningEventException | EventDoesNotExistException
        | InvalidOperationException e) {
      System.out.println(e.getMessage());
    }
  }

  public void runInteractiveMode() {
    EventManager eventManager = new EventManager();

    Scanner s = new Scanner(System.in);
    int option = 0;
    option = -1;
    while (option != 5) {
      System.out
          .println("(1) START_EVENT \n(2) STOP_EVENT \n(3) STATUS_OF_EVENT \n(4) STATUS_OF_ALL_EVENTS \n(5) EXIT");
      System.out.print("Choose [1,2,3,4,5]: ");
      option = s.nextInt();

      if (option == 1) {
        s.nextLine();
        System.out.print("(A)sync or (S)ync event?: ");
        String eventType = s.nextLine();
        System.out.print("How long should this event run for (in seconds)?: ");
        int eventTime = s.nextInt();
        if (eventType.equalsIgnoreCase("A")) {
          try {
            int eventID = eventManager.createAsyncEvent(eventTime);
            System.out.println("Event [" + eventID + "] has been created.");
            eventManager.startEvent(eventID);
            System.out.println("Event [" + eventID + "] has been started.");
          } catch (MaxNumOfEventsAllowedException | LongRunningEventException | EventDoesNotExistException e) {
            System.out.println(e.getMessage());
          }
        } else if (eventType.equalsIgnoreCase("S")) {
          try {
            int eventID = eventManager.createSyncEvent(eventTime);
            System.out.println("Event [" + eventID + "] has been created.");
            eventManager.startEvent(eventID);
            System.out.println("Event [" + eventID + "] has been started.");
          } catch (MaxNumOfEventsAllowedException | InvalidOperationException | LongRunningEventException
              | EventDoesNotExistException e) {
            System.out.println(e.getMessage());
          }
        } else {
          System.out.println("Unknown event type.");
        }
      } else if (option == 2) {
        System.out.print("Event ID: ");
        int eventID = s.nextInt();
        try {
          eventManager.stopEvent(eventID);
          System.out.println("Event [" + eventID + "] has been stopped.");
        } catch (EventDoesNotExistException e) {
          System.out.println(e.getMessage());
        }
      } else if (option == 3) {
        System.out.print("Event ID: ");
        int eventID = s.nextInt();
        try {
          eventManager.getStatus(eventID);
        } catch (EventDoesNotExistException e) {
          System.out.println(e.getMessage());
        }
      } else if (option == 4) {
        eventManager.getStatusOfAllEvents();
      }
    }

    s.close();
  }

}
