package com.iluwatar.saga.orchestration;

public class Utility {
    private Utility() {
    }

    public static void sleepInSec(int sec){
        try {
            Thread.sleep(sec*1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


}
