package com.iluwatar.utility;

import com.azure.core.util.BinaryData;
import com.azure.messaging.eventgrid.EventGridPublisherClient;
import com.iluwatar.domain.Message;
import com.iluwatar.domain.UsageDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.logging.Logger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventHandlerUtilityTest {

    @Mock
    EventGridPublisherClient<BinaryData> mockCustomEventClient;

    @InjectMocks
    EventHandlerUtility<Message<UsageDetail>> eventHandlerUtility;

    @BeforeEach
    public void setUp() {

        System.setProperty("EventGridURL", "https://www.dummyEndpoint.com/api/events");
        System.setProperty("EventGridKey", "EventGridURL");
    }

    @Test
    void testPublishEvent() {
        doNothing().when(mockCustomEventClient).sendEvent(any(BinaryData.class));
        eventHandlerUtility.publishEvent(null, Logger.getLogger("logger"));
        verify(mockCustomEventClient, times(1)).sendEvent(any(BinaryData.class));

    }

    @Test
    void testPublishEvent1() {
        eventHandlerUtility.publishEvent(null, null);
        verify(mockCustomEventClient, times(1)).sendEvent(any(BinaryData.class));
    }
}
