package com.example.shoppingapp.controller;

import com.example.shoppingapp.dto.Product.ProductResponseAdminDTO;
import com.example.shoppingapp.dto.Product.ProductResponseDTO;
import com.example.shoppingapp.dto.Product.ProductRequestDTO;
import com.example.shoppingapp.exception.ApiResponse;
import com.example.shoppingapp.serviceInterface.ProductService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<? extends ProductResponseDTO>>> getAllAvailableProducts() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
            List<? extends ProductResponseDTO> products = productService.getAllProductsAdmin();
            return ResponseEntity.ok(new ApiResponse<>(true, "Products fetched successfully", products));
        } else if (auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_USER"))) {
            List<? extends ProductResponseDTO> products = productService.getAllProductsUser();
            return ResponseEntity.ok(new ApiResponse<>(true, "Products fetched successfully", products));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiResponse<>(false, "Access denied", null));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<? extends ProductResponseDTO>> getProductById(@PathVariable Long productId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
            var product = productService.getProductByIdAdmin(productId);
            if (product != null) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Product fetched successfully", product));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Product not found", null));
            }
        } else if (auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_USER"))) {
            var product = productService.getProductByIdUser(productId);
            if (product != null) {
                return ResponseEntity.ok(new ApiResponse<>(true, "Product fetched successfully", product));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(false, "Product not found", null));
            }
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiResponse<>(false, "Access denied", null));
    }

    @GetMapping("/recent/{limit}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getRecentProducts(@PathVariable int limit) {
        List<ProductResponseDTO> recentProducts = productService.getRecentProducts(limit);
        return ResponseEntity.ok(new ApiResponse<>(true, "Recent products fetched successfully", recentProducts));
    }

    @GetMapping("/frequent/{limit}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getFrequentProducts(@PathVariable int limit) {
        List<ProductResponseDTO> frequentProducts = productService.getFrequentProducts(limit);
        return ResponseEntity.ok(new ApiResponse<>(true, "Frequent products fetched successfully", frequentProducts));
    }

    @PatchMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductRequestDTO productRequestDTO) {
        productService.updateProduct(productId, productRequestDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Product updated successfully", null));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> addProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        productService.addProduct(productRequestDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Product added successfully", null));
    }

    @GetMapping("/profit/{limit}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<ProductResponseAdminDTO>>> getTopProfitableProducts(@PathVariable int limit) {
        List<ProductResponseAdminDTO> topProfitableProducts = productService.getTopProfitableProducts(limit);
        return ResponseEntity.ok(new ApiResponse<>(true, "Top profitable products fetched successfully", topProfitableProducts));
    }

    @GetMapping("/popular/{limit}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<ProductResponseAdminDTO>>> getMostPopularProducts(@PathVariable int limit) {
        List<ProductResponseAdminDTO> mostPopularProducts = productService.getMostPopularProducts(limit);
        return ResponseEntity.ok(new ApiResponse<>(true, "Most popular products fetched successfully", mostPopularProducts));
    }

    @GetMapping("/totalSold")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Integer>> getTotalSoldProducts() {
        int totalSoldProducts = productService.getTotalSoldProducts();
        return ResponseEntity.ok(new ApiResponse<>(true, "Total sold products fetched successfully", totalSoldProducts));
    }
}