package com.iluwatar.utility;

import java.util.logging.Logger;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.BinaryData;
import com.azure.messaging.eventgrid.EventGridPublisherClient;
import com.azure.messaging.eventgrid.EventGridPublisherClientBuilder;

public class EventHandlerUtility<T> {

    private EventGridPublisherClient<BinaryData> customEventClient;

    public EventHandlerUtility() {
        this.customEventClient = new EventGridPublisherClientBuilder()
                .endpoint(System.getenv("EventGridURL"))
                .credential(new AzureKeyCredential(System.getenv("EventGridKey")))
                .buildCustomEventPublisherClient();
    }

    public EventHandlerUtility(EventGridPublisherClient<BinaryData> customEventClient) {
        this.customEventClient = customEventClient;
    }

    public void publishEvent(T customEvent, Logger logger){
        try{
            customEventClient.sendEvent(BinaryData.fromObject(customEvent));
        }catch(Exception e){
            logger.info(e.getMessage());
        }
    }    
}
