package com.iluwatar.utility;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.iluwatar.domain.*;
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

        MessageBody<UsageDetail> messageBody = new MessageBody<UsageDetail>();
        List<UsageDetail> usageDetailsList = new ArrayList<UsageDetail>();
        Random random = new Random();
        for (int i = 0; i < 51; i++) {
            UsageDetail usageDetail = new UsageDetail();
            usageDetail.setUserId("userId" + i);
            usageDetail.setData(random.nextInt(500));
            usageDetail.setDuration(random.nextInt(500));

            usageDetailsList.add(usageDetail);
        }
        messageBody.setData(usageDetailsList);

        // Create message header
        MessageHeader messageHeader = new MessageHeader();
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
    void testDropToPersistantStorage() {
        messageHandlerUtility.dropToPersistantStorage(messageToPublish, Logger.getLogger("logger"));
        verify(mockBlobServiceClient, times(1)).getBlobContainerClient(anyString());
        // verify(mockContainerClient, times(0)).exists();
    }

    @Test
    void testReadFromPersistantStorage() {

        messageHandlerUtility.readFromPersistantStorage(messageReference, Logger.getLogger("logger"));
        verify(mockBlobServiceClient, times(1)).getBlobContainerClient(anyString());
    }
}
