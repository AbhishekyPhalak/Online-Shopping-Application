package com.example.shoppingapp.dto.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponseDTO {
    private Long orderId;
    private LocalDateTime datePlaced;
    private String orderStatus;
    private List<OrderItemDTO> items;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItemDTO {
        private Long itemId;
        private Long productId;
        private String productName;
        private int quantity;
        private double purchasedPrice;
    }
}
