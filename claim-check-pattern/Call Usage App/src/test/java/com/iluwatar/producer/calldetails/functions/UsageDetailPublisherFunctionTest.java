package com.iluwatar.producer.calldetails.functions;

import com.iluwatar.HttpResponseMessageMock;
import com.iluwatar.domain.MessageHeader;
import com.iluwatar.domain.UsageDetail;
import com.iluwatar.utility.EventHandlerUtility;
import com.iluwatar.utility.MessageHandlerUtility;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit test for Function class.
 */
@ExtendWith(MockitoExtension.class)
public class UsageDetailPublisherFunctionTest {
    @Mock
    MessageHandlerUtility<UsageDetail> mockMessageHandlerUtility;
    @Mock
    EventHandlerUtility<MessageHeader> mockEventHandlerUtility;

    @InjectMocks
    UsageDetailPublisherFunction usageDetailPublisherFunction;

    /**
     * Unit test for HttpTriggerJava method.
     */
    @Test
    public void testHttpTriggerJavaWithSubscriptionValidationEventType() throws Exception {

        // Setup
        @SuppressWarnings("unchecked")
        final HttpRequestMessage<Optional<String>> req = mock(HttpRequestMessage.class);
        String fileAbsolutePath = getClass().getResource("/subscriptionValidationEvent.json").getPath()
                .replaceAll("%20", " "), jsonBody = Files.readString(Paths.get(fileAbsolutePath)).replaceAll("\n", " ");
        doReturn(Optional.of(jsonBody)).when(req).getBody();
        doAnswer(new Answer<HttpResponseMessage.Builder>() {
            @Override
            public HttpResponseMessage.Builder answer(InvocationOnMock invocation) {
                HttpStatus status = (HttpStatus) invocation.getArguments()[0];
                return new HttpResponseMessageMock.HttpResponseMessageBuilderMock().status(status);
            }
        }).when(req).createResponseBuilder(any(HttpStatus.class));

        final ExecutionContext context = mock(ExecutionContext.class);

        // Invoke
        final HttpResponseMessage ret = this.usageDetailPublisherFunction.run(req, context);

        // Verify
        assertEquals(ret.getStatus(), HttpStatus.OK);
    }

    @Test
    public void testHttpTriggerJavaWithUsageDetailEventType() throws Exception {

        // Setup
        @SuppressWarnings("unchecked")
        final HttpRequestMessage<Optional<String>> req = mock(HttpRequestMessage.class);
        String fileAbsolutePath = getClass().getResource("/usageDetailEvent.json").getPath().replaceAll("%20", " "),
                jsonBody = Files.readString(Paths.get(fileAbsolutePath)).replaceAll("\n", " ");
        doReturn(Optional.of(jsonBody)).when(req).getBody();
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
        final HttpResponseMessage ret = this.usageDetailPublisherFunction.run(req, context);

        // Verify
        assertEquals(ret.getStatus(), HttpStatus.OK);
    }

}
