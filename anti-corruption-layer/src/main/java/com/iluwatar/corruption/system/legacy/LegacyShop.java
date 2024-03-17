package com.iluwatar.corruption.system.legacy;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The class represents a legacy shop system. The main purpose is to place the order from the
 * customers.
 */
@Service
public class LegacyShop {
  @Autowired private LegacyStore store;

  /**
   * Places the order in the legacy system. If the order is already present in the modern system,
   * then the order is placed only if the data is the same. If the order is not present in the
   * modern system, then the order is placed in the legacy system.
   */
  public void placeOrder(LegacyOrder legacyOrder) {
    store.put(legacyOrder.getId(), legacyOrder);
  }

  /** Finds the order in the legacy system. */
  public Optional<LegacyOrder> findOrder(String orderId) {
    return store.get(orderId);
  }
}
