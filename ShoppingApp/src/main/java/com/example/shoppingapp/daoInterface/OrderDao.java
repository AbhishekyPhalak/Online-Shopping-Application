package com.example.shoppingapp.daoInterface;

import com.example.shoppingapp.entity.Order;
import com.example.shoppingapp.entity.Product;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
    void save(Order order);
    Optional<Order> findById(Long orderId);
    void update(Order order);
    List<Order> findByUserId(Long userId);
    List<Order> findAllOrders(org.springframework.data.domain.Pageable pageable);
}