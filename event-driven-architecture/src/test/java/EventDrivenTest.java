import com.iluwatar.eda.EventDispatcher;
import com.iluwatar.eda.event.Event;
import com.iluwatar.eda.event.UserCreatedEvent;
import com.iluwatar.eda.event.UserUpdatedEvent;
import com.iluwatar.eda.handler.UserCreatedEventHandler;
import com.iluwatar.eda.handler.UserUpdatedEventHandler;
import com.iluwatar.eda.model.User;

import org.junit.Test;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.junit.Assert.assertEquals;


/**
 * Event Driven Pattern unit tests to assert and verify correct pattern behaviour
 */
public class EventDrivenTest {

  /**
   * This unit test should register events and event handlers correctly with the event dispatcher
   * and events should be dispatched accordingly.
   */
  @Test
  public void testEventDriverPattern() {

    EventDispatcher dispatcher = spy(new EventDispatcher());
    UserCreatedEventHandler userCreatedEventHandler = new UserCreatedEventHandler();
    UserUpdatedEventHandler userUpdatedEventHandler = new UserUpdatedEventHandler();
    dispatcher.registerChannel(UserCreatedEvent.class, userCreatedEventHandler);
    dispatcher.registerChannel(UserUpdatedEvent.class, userUpdatedEventHandler);

    assertEquals("Two handlers must be registered", 2, dispatcher.getHandlers().size());
    assertEquals("UserCreatedEvent must return the UserCreatedEventHandler",
            userCreatedEventHandler,
            (UserCreatedEventHandler) dispatcher.getHandlers().get(UserCreatedEvent.class));
    assertEquals("UserUpdatedEvent must be registered to the UserUpdatedEventHandler",
            userUpdatedEventHandler,
            (UserUpdatedEventHandler) dispatcher.getHandlers().get(UserUpdatedEvent.class));

    User user = new User("iluwatar");

    UserCreatedEvent userCreatedEvent = new UserCreatedEvent(user);
    UserUpdatedEvent userUpdatedEvent = new UserUpdatedEvent(user);
    dispatcher.dispatch(userCreatedEvent);
    dispatcher.dispatch(userUpdatedEvent);

    //verify that the events have been dispatched
    verify(dispatcher).dispatch(userCreatedEvent);
    verify(dispatcher).dispatch(userUpdatedEvent);
  }

  /**
   * This unit test should correctly return the {@link Event} class type when calling the
   * {@link Event#getType() getType} method.
   */
  @Test
  public void testGetEventType() {
    User user = new User("iluwatar");
    UserCreatedEvent userCreatedEvent = new UserCreatedEvent(user);
    assertEquals(UserCreatedEvent.class, userCreatedEvent.getType());
  }
}
