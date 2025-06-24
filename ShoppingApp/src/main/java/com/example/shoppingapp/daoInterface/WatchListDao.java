package com.example.shoppingapp.daoInterface;

import com.example.shoppingapp.entity.Product;
import com.example.shoppingapp.entity.User;

import java.util.List;

public interface WatchListDao {
    void add(User user, Product product);
    void remove(Long userId, Long productId);
    List<Product> findInStockProductsByUserId(Long userId);
    boolean existsByUserIdAndProductId(Long userId, Long productId);
}