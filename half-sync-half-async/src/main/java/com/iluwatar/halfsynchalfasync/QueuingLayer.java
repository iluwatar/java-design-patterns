package com.iluwatar.halfsynchalfasync;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class QueuingLayer {
	BlockingQueue<Runnable> incomingQueue = new LinkedBlockingQueue<>();
}
