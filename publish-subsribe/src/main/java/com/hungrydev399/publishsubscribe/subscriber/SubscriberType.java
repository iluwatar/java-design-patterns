package com.hungrydev399.publishsubscribe.subscriber;

public enum SubscriberType {
  NONDURABLE,    // Regular non-durable subscriber
  DURABLE,       // Durable subscriber that receives messages even when offline
  SHARED,        // Shared subscription where multiple subscribers share the load
  SHARED_DURABLE // Combination of shared and durable subscription
}
