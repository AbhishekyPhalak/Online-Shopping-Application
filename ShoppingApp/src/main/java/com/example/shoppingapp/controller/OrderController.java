package com.example.shoppingapp.controller;

import com.example.shoppingapp.dto.Order.OrderDetailResponseDTO;
import com.example.shoppingapp.dto.Order.OrderRequestDTO;
import com.example.shoppingapp.dto.Order.OrderResponseDTO;
import com.example.shoppingapp.exception.ApiResponse;
import com.example.shoppingapp.serviceInterface.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<Void>> createOrder(@Valid @RequestBody OrderRequestDTO orderRequest) {
        orderService.placeOrder(orderRequest);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order created successfully", null));
    }

    @PatchMapping("{orderId}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order cancelled successfully", null));
    }

    @PatchMapping("{orderId}/complete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> completeOrder(@PathVariable Long orderId) {
        orderService.completeOrder(orderId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order marked as completed successfully", null));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getAllOrdersByUser(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {

        List<OrderResponseDTO> orders = orderService.getOrdersForCurrentUser(page, size);
        return ResponseEntity.ok(new ApiResponse<>(true, "Orders fetched successfully", orders));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<? extends OrderDetailResponseDTO>> getOrderDetails(@PathVariable Long orderId) {
        OrderDetailResponseDTO orderDetails = orderService.getOrderDetails(orderId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order details fetched successfully", orderDetails));
    }
}