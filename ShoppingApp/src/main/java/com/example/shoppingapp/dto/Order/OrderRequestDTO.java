package com.example.shoppingapp.dto.Order;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class OrderRequestDTO {
    @NotEmpty(message = "Order list must not be empty")
    private List<@Valid OrderItemRequestDTO> order;
}
