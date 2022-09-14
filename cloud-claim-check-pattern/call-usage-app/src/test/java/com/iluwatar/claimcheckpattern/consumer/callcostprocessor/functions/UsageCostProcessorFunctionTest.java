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
package com.iluwatar.claimcheckpattern.consumer.callcostprocessor.functions;

import com.iluwatar.claimcheckpattern.HttpResponseMessageMock;
import com.iluwatar.claimcheckpattern.domain.Message;
import com.iluwatar.claimcheckpattern.domain.MessageBody;
import com.iluwatar.claimcheckpattern.domain.MessageHeader;
import com.iluwatar.claimcheckpattern.domain.MessageReference;
import com.iluwatar.claimcheckpattern.domain.UsageCostDetail;
import com.iluwatar.claimcheckpattern.domain.UsageDetail;
import com.iluwatar.claimcheckpattern.utility.MessageHandlerUtility;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.logging.Logger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit test for Function class.
 */
@ExtendWith(MockitoExtension.class)
public class UsageCostProcessorFunctionTest {

    @Mock
    MessageHandlerUtility<UsageDetail> mockMessageHandlerUtilityForUsageADetail;
    @Mock
    MessageHandlerUtility<UsageCostDetail> mockMessageHandlerUtilityForUsageCostDetail;
    @Mock
    ExecutionContext context;

    Message<UsageCostDetail> messageToDrop;
    Message<UsageDetail> messageToRead;
    MessageReference messageReference;
    @InjectMocks
    UsageCostProcessorFunction usageCostProcessorFunction;

    @BeforeEach
    public void setUp() {
        var messageBodyUsageDetail = new MessageBody<UsageDetail>();
        var usageDetailsList = new ArrayList<UsageDetail>();

        var messageBodyUsageCostDetail = new MessageBody<UsageCostDetail>();
        var usageCostDetailsList = new ArrayList<UsageCostDetail>();
        for (int i = 0; i < 2; i++) {
            var usageDetail = new UsageDetail();
            usageDetail.setUserId("userId" + i);
            usageDetail.setData(i + 1);
            usageDetail.setDuration(i + 1);
            usageDetailsList.add(usageDetail);

            var usageCostDetail = new UsageCostDetail();
            usageCostDetail.setUserId(usageDetail.getUserId());
            usageCostDetail.setDataCost(usageDetail.getData() * 0.20);
            usageCostDetail.setCallCost(usageDetail.getDuration() * 0.30);
            usageCostDetailsList.add(usageCostDetail);
        }
        messageBodyUsageDetail.setData(usageDetailsList);
        messageBodyUsageCostDetail.setData(usageCostDetailsList);

        // Create message header
        var messageHeader = new MessageHeader();
        messageHeader.setId(UUID.randomUUID().toString());
        messageHeader.setSubject("UsageDetailPublisher");
        messageHeader.setTopic("usagecostprocessorfunction-topic");
        messageHeader.setEventType("UsageDetail");
        messageHeader.setEventTime(OffsetDateTime.now().toString());
        this.messageReference = new MessageReference("callusageapp", "d8284456-dfff-4bd4-9cef-ea99f70f4835/input.json");
        messageHeader.setData(messageReference);
        messageHeader.setDataVersion("v1.0");

        // Create entire message
        messageToRead = new Message<>();
        messageToRead.setMessageHeader(messageHeader);
        messageToRead.setMessageBody(messageBodyUsageDetail);

        messageToDrop = new Message<>();
        messageToDrop.setMessageHeader(messageHeader);
        messageToDrop.setMessageBody(messageBodyUsageCostDetail);

    }

    /**
     * Unit test for HttpTriggerJava method.
     */
    @Test
    public void shouldTriggerHttpAzureFunctionJavaWithSubscriptionValidationEventType() throws Exception {

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
        final HttpResponseMessage ret = this.usageCostProcessorFunction.run(req, context);

        // Verify
        assertEquals(ret.getStatus(), HttpStatus.OK);
    }

    @Test
    public void shouldTriggerHttpAzureFunctionJavaWithUsageDetailEventType() throws Exception {
        // Setup
        @SuppressWarnings("unchecked")
        final HttpRequestMessage<Optional<String>> req = mock(HttpRequestMessage.class);
        String fileAbsolutePath = getClass().getResource("/usageDetailEvent.json").getPath().replaceAll("%20", " "),
                jsonBody = Files.readString(Paths.get(fileAbsolutePath)).replaceAll("\n", " ");
        doReturn(Optional.of(jsonBody)).when(req).getBody();
        doReturn(Logger.getGlobal()).when(context).getLogger();

        when(this.mockMessageHandlerUtilityForUsageADetail.readFromPersistantStorage(any(MessageReference.class),
                eq(Logger.getGlobal()))).thenReturn(messageToRead);
        doAnswer(new Answer<HttpResponseMessage.Builder>() {
            @Override
            public HttpResponseMessage.Builder answer(InvocationOnMock invocation) {
                HttpStatus status = (HttpStatus) invocation.getArguments()[0];
                return new HttpResponseMessageMock.HttpResponseMessageBuilderMock().status(status);
            }
        }).when(req).createResponseBuilder(any(HttpStatus.class));

        assertNotNull(this.mockMessageHandlerUtilityForUsageADetail);
        assertEquals(this.messageToRead, this.mockMessageHandlerUtilityForUsageADetail
                .readFromPersistantStorage(this.messageReference, Logger.getGlobal()));

        // Invoke
        final HttpResponseMessage ret = this.usageCostProcessorFunction.run(req, context);

        // Verify
        assertEquals(HttpStatus.OK, ret.getStatus());
        assertEquals("Message is dropped successfully", ret.getBody());
    }

}
