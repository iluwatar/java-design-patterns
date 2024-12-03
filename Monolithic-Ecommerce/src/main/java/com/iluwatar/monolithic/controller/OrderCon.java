package com.iluwatar.monolithic.controller;

import com.iluwatar.monolithic.model.User;
import com.iluwatar.monolithic.model.Orders;
import com.iluwatar.monolithic.model.Products;
import com.iluwatar.monolithic.repository.OrderRepo;
import com.iluwatar.monolithic.repository.ProductRepo;
import com.iluwatar.monolithic.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderCon {
    @Autowired
    private OrderRepo orderRepository;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private ProductRepo productRepository;

    public Orders placeOrder(Long userId, Long productId, Integer quantity) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Products product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new RuntimeException("Product not found");
}

        if (product.getStock() < quantity) {
            throw new RuntimeException("Not enough stock");
        }

        product.setStock(product.getStock() - quantity);
        productRepository.save(product);

        Orders order = new Orders(null, user, product, quantity, product.getPrice() * quantity);
        return orderRepository.save(order);
    }
}
