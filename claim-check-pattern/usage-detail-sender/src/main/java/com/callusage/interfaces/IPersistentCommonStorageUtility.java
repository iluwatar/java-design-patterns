package com.callusage.interfaces;

import org.springframework.stereotype.Service;

import com.callusage.domain.Message;
import com.callusage.domain.MessageHeader;


public interface IPersistentCommonStorageUtility {

    public Message readMessageFromPersistentStorage(MessageHeader messageHeader);
    public void dropMessageToPersistentStorage(Message message);

}
