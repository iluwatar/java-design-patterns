package com.iluwatar.monitor;

/**
 * Interface for listener to be registered and notified by the Monitor. 
 */
public interface MonitorListener {

  void nameThisThread(String name);

  void callEnterMonitor(AbstractMonitor monitor);

  void returnFromEnterMonitor(AbstractMonitor monitor);

  void leaveMonitor(AbstractMonitor monitor);

  void callAwait(Condition condition, AbstractMonitor monitor);

  void returnFromAwait(Condition condition, AbstractMonitor monitor);

  void signallerAwakesAwaitingThread(Condition condition, AbstractMonitor monitor);

  void signallerLeavesTemporarily(Condition condition, AbstractMonitor monitor);

  void signallerReenters(Condition condition, AbstractMonitor monitor);

  void signallerLeavesMonitor(Condition condition, AbstractMonitor monitor);

}