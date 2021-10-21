package com.iluwatar.consumer.callcostprocessor.functions;

import org.junit.jupiter.api.Test;
import com.azure.core.util.BinaryData;
import com.azure.messaging.eventgrid.EventGridEvent;
import com.azure.messaging.eventgrid.systemevents.SubscriptionValidationEventData;
import com.iluwatar.HttpResponseMessageMock;
import com.microsoft.azure.functions.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import java.util.*;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class UsageCostProcessorFunctionTest {
    @Test
    void testRun() {
        // Setup
        @SuppressWarnings("unchecked")
        final HttpRequestMessage<List<EventGridEvent>> req = mock(HttpRequestMessage.class);

        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("name", "Azure");
        doReturn(queryParams).when(req).getQueryParameters();

        SubscriptionValidationEventData subscriptionValidationEventData = new SubscriptionValidationEventData();
        EventGridEvent eventGridEvent = new EventGridEvent("subject", "Microsoft.EventGrid.SubscriptionValidationEvent",
                BinaryData.fromObject(subscriptionValidationEventData), "dataVersion");
        List<EventGridEvent> eventGridEvents = new ArrayList<>();
        eventGridEvents.add(eventGridEvent);
        Optional<List<EventGridEvent>> queryBody = Optional.of(eventGridEvents);
        doReturn(queryBody.get()).when(req).getBody();
        doAnswer(new Answer<HttpResponseMessage.Builder>() {
            @Override
            public HttpResponseMessage.Builder answer(InvocationOnMock invocation) {
                HttpStatus status = (HttpStatus) invocation.getArguments()[0];
                return new HttpResponseMessageMock.HttpResponseMessageBuilderMock().status(status);
            }
        }).when(req).createResponseBuilder(any(HttpStatus.class));

        final ExecutionContext context = mock(ExecutionContext.class);
        doReturn(Logger.getGlobal()).when(context).getLogger();

        // Invoke
        final HttpResponseMessage ret = new UsageCostProcessorFunction().run(req, context);

        // Verify
        assertEquals(ret.getStatus(), HttpStatus.OK);
    }
}
