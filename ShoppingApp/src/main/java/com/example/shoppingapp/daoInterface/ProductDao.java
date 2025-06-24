package com.example.shoppingapp.daoInterface;
import com.example.shoppingapp.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    List<Product> findAvailableProducts();
    Optional<Product> fetchProductById(Long productId);
    List<Product> findRecentProducts(int limit, Long userId);
    List<Product> findFrequentProducts(int limit, Long userId);
    void save(Product product);
    List<Product> findTopProfitableProducts(int limit);
    List<Product> findMostPopularProducts(int limit);
    int findTotalSoldProducts();
}


