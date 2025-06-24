package com.example.shoppingapp.serviceInterface;

import com.example.shoppingapp.dto.Order.OrderDetailResponseDTO;
import com.example.shoppingapp.dto.Order.OrderRequestDTO;
import com.example.shoppingapp.dto.Order.OrderResponseDTO;
import com.example.shoppingapp.dto.Product.ProductResponseDTO;
import org.aspectj.weaver.Lint;

import java.util.List;

public interface OrderService {
    void placeOrder(OrderRequestDTO orderRequestDTO);
    void cancelOrder(Long orderId);
    OrderDetailResponseDTO getOrderDetails(Long orderId);
    List<OrderResponseDTO> getOrdersForCurrentUser(int page, int size);
    void completeOrder(Long orderId);
}
