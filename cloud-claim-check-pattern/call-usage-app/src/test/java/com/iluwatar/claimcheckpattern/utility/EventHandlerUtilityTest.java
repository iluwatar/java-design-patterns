/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.claimcheckpattern.utility;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.azure.core.util.BinaryData;
import com.azure.messaging.eventgrid.EventGridPublisherClient;
import com.iluwatar.claimcheckpattern.domain.Message;
import com.iluwatar.claimcheckpattern.domain.UsageDetail;
import java.util.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void shouldPublishEvent() {
        doNothing().when(mockCustomEventClient).sendEvent(any(BinaryData.class));
        eventHandlerUtility.publishEvent(null, Logger.getLogger("logger"));
        verify(mockCustomEventClient, times(1)).sendEvent(any(BinaryData.class));

    }

    @Test
    void shouldPublishEventWithNullLogger() {
        eventHandlerUtility.publishEvent(null, null);
        verify(mockCustomEventClient, times(1)).sendEvent(any(BinaryData.class));
    }
}
