package com.iluwatar.monolithic.controller;
import com.iluwatar.monolithic.exceptions.InsufficientStockException;
import com.iluwatar.monolithic.exceptions.NonExistentProductException;
import com.iluwatar.monolithic.exceptions.NonExistentUserException;
import com.iluwatar.monolithic.model.Orders;
import com.iluwatar.monolithic.model.Products;
import com.iluwatar.monolithic.model.User;
import com.iluwatar.monolithic.repository.OrderRepo;
import com.iluwatar.monolithic.repository.ProductRepo;
import com.iluwatar.monolithic.repository.UserRepo;
import org.springframework.stereotype.Service;
/**
 * OrderCon is a controller class for managing Order operations.
 * */
@Service
public class OrderCon {
  private final OrderRepo orderRepository;
  private final UserRepo userRepository;
  private final ProductRepo productRepository;
  /**
   * This function handles the initializing of the controller.
   * */
  public OrderCon(OrderRepo orderRepository, UserRepo userRepository, ProductRepo productRepository) {
    this.orderRepository = orderRepository;
    this.userRepository = userRepository;
    this.productRepository = productRepository;
  }
  /**
   * This function handles placing orders with all of its cases.
   * */
  public Orders placeOrder(Long userId, Long productId, Integer quantity) {
    User user = userRepository.findById(userId).orElse(null);
    if (user == null) {
      throw new NonExistentUserException("User with ID " + userId + " not found");
    }

    Products product = productRepository.findById(productId).orElse(null);
    if (product == null) {
      throw new NonExistentProductException("Product with ID " + productId + " not found");
    }

    if (product.getStock() < quantity) {
      throw new InsufficientStockException("Not enough stock for product " + productId);
    }

    product.setStock(product.getStock() - quantity);
    productRepository.save(product);

    Orders order = new Orders(null, user, product, quantity, product.getPrice() * quantity);
    return orderRepository.save(order);
  }
}
