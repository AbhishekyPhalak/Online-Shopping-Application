package com.example.shoppingapp.controller;

import com.example.shoppingapp.dto.Product.ProductResponseDTO;
import com.example.shoppingapp.exception.ApiResponse;
import com.example.shoppingapp.serviceInterface.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/watchlist")
@PreAuthorize("hasRole('USER')")
public class WatchListController {

    private final WatchListService watchListService;

    @Autowired
    public WatchListController(WatchListService watchListService) {
        this.watchListService = watchListService;
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<Void>> addProductToWatchList(@PathVariable Long productId) {
        watchListService.addToWatchlist(productId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Product added to watchlist successfully", null));
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<ApiResponse<Void>> removeFromWatchlist(@PathVariable Long productId) {
        watchListService.removeFromWatchlist(productId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Product removed from watchlist", null));
    }

    @GetMapping("/products/all")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getWatchlist() {
        List<ProductResponseDTO> watchlist = watchListService.getWatchlist();
        return ResponseEntity.ok(new ApiResponse<>(true, "Watchlist fetched successfully", watchlist));
    }
}