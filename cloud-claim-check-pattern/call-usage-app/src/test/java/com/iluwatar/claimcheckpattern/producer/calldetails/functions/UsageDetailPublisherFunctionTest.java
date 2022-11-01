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
package com.iluwatar.claimcheckpattern.producer.calldetails.functions;

import com.iluwatar.claimcheckpattern.HttpResponseMessageMock;
import com.iluwatar.claimcheckpattern.domain.MessageHeader;
import com.iluwatar.claimcheckpattern.domain.UsageDetail;
import com.iluwatar.claimcheckpattern.utility.EventHandlerUtility;
import com.iluwatar.claimcheckpattern.utility.MessageHandlerUtility;
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
class UsageDetailPublisherFunctionTest {
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
    void shouldTriggerHttpAzureFunctionJavaWithSubscriptionValidationEventType() throws Exception {

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
    void shouldTriggerHttpAzureFunctionJavaWithUsageDetailEventType() throws Exception {

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
