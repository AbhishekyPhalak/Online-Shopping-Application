package com.example.shoppingapp.serviceInterface;

import com.example.shoppingapp.dto.Product.ProductResponseAdminDTO;
import com.example.shoppingapp.dto.Product.ProductResponseDTO;
import com.example.shoppingapp.dto.Product.ProductRequestDTO;

import java.util.List;

public interface ProductService {
    List<ProductResponseDTO> getAllProductsUser();
    ProductResponseDTO getProductByIdUser(Long productId);
    List<ProductResponseDTO> getRecentProducts(int limit);
    List<ProductResponseDTO> getFrequentProducts(int limit);
    List<ProductResponseAdminDTO> getAllProductsAdmin();
    ProductResponseAdminDTO getProductByIdAdmin(Long productId);
    void updateProduct(Long productId, ProductRequestDTO productRequestDTO);
    void addProduct(ProductRequestDTO productRequestDTO);
    List<ProductResponseAdminDTO> getMostPopularProducts(int limit);
    List<ProductResponseAdminDTO> getTopProfitableProducts(int limit);
    int getTotalSoldProducts();
}
