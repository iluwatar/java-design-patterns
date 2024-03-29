package com.iluwatar.corruption.system.modern;

import com.iluwatar.corruption.system.AntiCorruptionLayer;
import com.iluwatar.corruption.system.ShopException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The class represents a modern shop system.
 * The main purpose of the class is to place orders and find orders.
 */
@Service
public class ModernShop {
  @Autowired
  private ModernStore store;

  @Autowired
  private AntiCorruptionLayer acl;

  /**
   * Places the order in the modern system.
   * If the order is already present in the legacy system, then no need to place it again.
   */
  public void placeOrder(ModernOrder order) throws ShopException {

    String id = order.getId();
    // check if the order is already present in the legacy system
    Optional<ModernOrder> orderInObsoleteSystem = acl.findOrderInLegacySystem(id);

    if (orderInObsoleteSystem.isPresent()) {
      var legacyOrder = orderInObsoleteSystem.get();
      if (!order.equals(legacyOrder)) {
        throw ShopException.throwIncorrectData(legacyOrder.toString(), order.toString());
      }
    } else {
      store.put(id, order);
    }
  }

  /**
   * Finds the order in the modern system.
   */
  public Optional<ModernOrder> findOrder(String orderId) {
    return store.get(orderId);
  }
}
