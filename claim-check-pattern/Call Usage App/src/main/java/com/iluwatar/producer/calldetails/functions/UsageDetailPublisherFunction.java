package com.iluwatar.producer.calldetails.functions;

import com.azure.messaging.eventgrid.EventGridEvent;
import com.azure.messaging.eventgrid.systemevents.SubscriptionValidationEventData;
import com.azure.messaging.eventgrid.systemevents.SubscriptionValidationResponse;
import com.iluwatar.domain.Message;
import com.iluwatar.domain.MessageBody;
import com.iluwatar.domain.MessageHeader;
import com.iluwatar.domain.MessageReference;
import com.iluwatar.domain.UsageDetail;
import com.iluwatar.utility.EventHandlerUtility;
import com.iluwatar.utility.MessageHandlerUtility;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

/**
 * Azure Functions with HTTP Trigger.
 */
public class UsageDetailPublisherFunction {
    @FunctionName("UsageDetailPublisherFunction")
    public HttpResponseMessage run(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.GET, HttpMethod.POST},
                authLevel = AuthorizationLevel.ANONYMOUS)
                HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        String eventGridJsonString = request.getBody().get();
        List<EventGridEvent> eventGridEvents = EventGridEvent.fromString(eventGridJsonString);

        for(EventGridEvent eventGridEvent : eventGridEvents)
        {
            // Handle system events
            if (eventGridEvent.getEventType().equals("Microsoft.EventGrid.SubscriptionValidationEvent"))
            {
                SubscriptionValidationEventData subscriptionValidationEventData = eventGridEvent.getData().toObject(SubscriptionValidationEventData.class);
                // Handle the subscription validation event
                SubscriptionValidationResponse responseData = new SubscriptionValidationResponse();
                responseData.setValidationResponse(subscriptionValidationEventData.getValidationCode());
                return request.createResponseBuilder(HttpStatus.OK).body(responseData).build();
                
            }
            else if(eventGridEvent.getEventType().equals("UsageDetail"))
            {
                // Create message body
                MessageBody<UsageDetail> messageBody = new MessageBody<UsageDetail>();
                List<UsageDetail> usageDetailsList = new ArrayList<UsageDetail>();
                Random random = new Random();
                for(int i=0;i<51;i++){
                    UsageDetail usageDetail = new UsageDetail();
                    usageDetail.setUserId("userId"+i);
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
                MessageReference messageReference = new MessageReference("callusageapp",messageHeader.getId()+"/input.json");
                messageHeader.setData(messageReference);
                messageHeader.setDataVersion("v1.0");

                // Create entire message
                Message<UsageDetail> message = new Message<>();
                message.setMessageHeader(messageHeader);
                message.setMessageBody(messageBody);

                // Drop data to persistent storage
                MessageHandlerUtility<UsageDetail> messageHandlerUtility = new MessageHandlerUtility<>();
                messageHandlerUtility.dropToPersistantStorage(message,context.getLogger());

                // Publish event to event grid topic
                EventHandlerUtility<MessageHeader> eventHandlerUtility = new EventHandlerUtility<>();
                eventHandlerUtility.publishEvent(messageHeader,context.getLogger());

                context.getLogger().info("Message is dropped and event is published successfully");
                return request.createResponseBuilder(HttpStatus.OK).body(message).build();
            }
        }
        return request.createResponseBuilder(HttpStatus.OK).body(null).build();
    }
}
