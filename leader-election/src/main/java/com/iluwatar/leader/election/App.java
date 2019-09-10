package com.iluwatar.leader.election;

import java.util.HashMap;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        MessageManager messageManager = new TokenRingMessageManager();
        TokenRingInstance instance1 = new TokenRingInstance(messageManager, 1, 1, true);
        TokenRingInstance instance2 = new TokenRingInstance(messageManager, 2, 1, true);
        TokenRingInstance instance3 = new TokenRingInstance(messageManager, 3, 1, true);
        TokenRingInstance instance4 = new TokenRingInstance(messageManager, 4, 1, true);
        Map<Integer, Instance> map = new HashMap<Integer, Instance>();
        map.put(1, instance1);
        map.put(2, instance2);
        map.put(3, instance3);
        map.put(4, instance4);
        ((TokenRingMessageManager) messageManager).setInstanceMap(map);
        Thread t1 = new Thread(instance1);
        Thread t2 = new Thread(instance2);
        Thread t3 = new Thread(instance3);
        Thread t4 = new Thread(instance4);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        return;
    }
}
