package com.callusage.interfaces;

import com.callusage.domain.Message;
import com.callusage.domain.MessageHeader;

public interface IPersistentCommonStorageUtility {

    public Message readMessageFromPersistentStorage(MessageHeader messageHeader);
    public void dropMessageToPersistentStorage(Message message);

}
