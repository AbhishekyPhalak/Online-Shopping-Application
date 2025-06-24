package com.example.shoppingapp.serviceInterface;

import com.example.shoppingapp.dto.Product.ProductResponseDTO;

import java.util.List;

public interface WatchListService {
    void addToWatchlist(Long productId);
    void removeFromWatchlist(Long productId);
    List<ProductResponseDTO> getWatchlist();
}