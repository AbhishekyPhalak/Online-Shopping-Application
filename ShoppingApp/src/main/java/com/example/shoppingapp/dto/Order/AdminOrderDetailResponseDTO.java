package com.example.shoppingapp.dto.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class AdminOrderDetailResponseDTO extends OrderDetailResponseDTO {
    private List<AdminOrderItemDTO> adminItems;
    private Long userId;
    private String username;

    public AdminOrderDetailResponseDTO(Long orderId, LocalDateTime datePlaced, String orderStatus,
                                       List<AdminOrderItemDTO> adminItems, Long userId, String username) {
        super(orderId, datePlaced, orderStatus, null);
        this.adminItems = adminItems;
        this.userId = userId;
        this.username = username;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AdminOrderItemDTO extends OrderItemDTO {
        private double wholesalePrice;

        public AdminOrderItemDTO(Long itemId, Long productId, String productName,
                                 int quantity, double purchasedPrice, double wholesalePrice) {
            super(itemId, productId, productName, quantity, purchasedPrice);
            this.wholesalePrice = wholesalePrice;
        }
    }
}