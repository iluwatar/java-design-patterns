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

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.iluwatar.claimcheckpattern.domain.*;
import com.iluwatar.claimcheckpattern.utility.MessageHandlerUtility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageHandlerUtilityTest {
    @Mock
    private BlobClient mockBlobClient;

    @Mock
    private BlobContainerClient mockContainerClient;

    @Mock
    private BlobServiceClient mockBlobServiceClient;

    @InjectMocks
    private MessageHandlerUtility<UsageDetail> messageHandlerUtility;

    private Message<UsageDetail> messageToPublish;
    private MessageReference messageReference;

    @BeforeEach
    public void setUp() {
        System.setProperty("BlobStorageConnectionString", "https://www.dummyEndpoint.com/api/blobs");

        var messageBody = new MessageBody<UsageDetail>();
        var usageDetailsList = new ArrayList<UsageDetail>();
        var random = new Random();
        for (int i = 0; i < 51; i++) {
            var usageDetail = new UsageDetail();
            usageDetail.setUserId("userId" + i);
            usageDetail.setData(random.nextInt(500));
            usageDetail.setDuration(random.nextInt(500));

            usageDetailsList.add(usageDetail);
        }
        messageBody.setData(usageDetailsList);

        // Create message header
        var messageHeader = new MessageHeader();
        messageHeader.setId(UUID.randomUUID().toString());
        messageHeader.setSubject("UsageDetailPublisher");
        messageHeader.setTopic("usagecostprocessorfunction-topic");
        messageHeader.setEventType("UsageDetail");
        messageHeader.setEventTime(OffsetDateTime.now().toString());
        this.messageReference = new MessageReference("callusageapp", messageHeader.getId() + "/input.json");
        messageHeader.setData(messageReference);
        messageHeader.setDataVersion("v1.0");

        // Create entire message
        this.messageToPublish = new Message<>();
        this.messageToPublish.setMessageHeader(messageHeader);
        this.messageToPublish.setMessageBody(messageBody);

        when(mockContainerClient.getBlobClient(anyString())).thenReturn(mockBlobClient);
        when(mockBlobServiceClient.getBlobContainerClient(anyString())).thenReturn(mockContainerClient);
    }

    @Test
    void shouldDropMessageToPersistantStorage() {
        messageHandlerUtility.dropToPersistantStorage(messageToPublish, Logger.getLogger("logger"));
        verify(mockBlobServiceClient, times(1)).getBlobContainerClient(anyString());
        // verify(mockContainerClient, times(0)).exists();
    }

    @Test
    void shouldReadMessageFromPersistantStorage() {

        messageHandlerUtility.readFromPersistantStorage(messageReference, Logger.getLogger("logger"));
        verify(mockBlobServiceClient, times(1)).getBlobContainerClient(anyString());
    }
}
