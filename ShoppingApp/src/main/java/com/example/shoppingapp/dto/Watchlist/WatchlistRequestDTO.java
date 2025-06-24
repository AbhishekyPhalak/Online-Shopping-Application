package com.example.shoppingapp.dto.Watchlist;

import lombok.Data;

@Data
public class WatchlistRequestDTO {
    private Long userId;
    private Long productId;
}