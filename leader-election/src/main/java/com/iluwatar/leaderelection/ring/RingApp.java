package com.iluwatar.leaderelection.ring;

import com.iluwatar.leaderelection.Instance;
import com.iluwatar.leaderelection.MessageManager;
import com.iluwatar.leaderelection.MessageType;

import java.util.HashMap;
import java.util.Map;

public class RingApp {

    public static void main(String[] args) {

        Map<Integer, Instance> instanceMap = new HashMap<>();
        MessageManager messageManager = new RingMessageManager(instanceMap);

        RingInstance instance1 = new RingInstance(messageManager, 1, 1);
        RingInstance instance2 = new RingInstance(messageManager, 2, 1);
        RingInstance instance3 = new RingInstance(messageManager, 3, 1);
        RingInstance instance4 = new RingInstance(messageManager, 4, 1);
        RingInstance instance5 = new RingInstance(messageManager, 5, 1);

        instanceMap.put(1, instance1);
        instanceMap.put(2, instance2);
        instanceMap.put(3, instance3);
        instanceMap.put(4, instance4);
        instanceMap.put(5, instance5);

        instance2.onMessage(new RingMessage(MessageType.HEARTBEAT_INVOKE, ""));

        Thread thread1 = new Thread(instance1);
        Thread thread2 = new Thread(instance2);
        Thread thread3 = new Thread(instance3);
        Thread thread4 = new Thread(instance4);
        Thread thread5 = new Thread(instance5);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        instance1.setAlive(false);
    }
}
