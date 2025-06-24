package com.example.shoppingapp.serviceImplementation;

import com.example.shoppingapp.daoInterface.ProductDao;
import com.example.shoppingapp.daoInterface.UserDao;
import com.example.shoppingapp.dto.Product.ProductResponseAdminDTO;
import com.example.shoppingapp.dto.Product.ProductResponseDTO;
import com.example.shoppingapp.dto.Product.ProductRequestDTO;
import com.example.shoppingapp.entity.Product;
import com.example.shoppingapp.entity.User;
import com.example.shoppingapp.exception.ProductNotFoundException;
import com.example.shoppingapp.exception.UserNotFoundException;
import com.example.shoppingapp.serviceInterface.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;
    private final UserDao userDao;

    @Autowired
    public ProductServiceImpl(ProductDao productDao , UserDao userDao) {
        this.productDao = productDao;
        this.userDao = userDao;
    }

    @Override
    public List<ProductResponseDTO> getAllProductsUser() {
        return productDao.findAvailableProducts()
                .stream()
                .map(p -> {
                    ProductResponseDTO dto = new ProductResponseDTO();
                    dto.setProductId(p.getProductId());
                    dto.setName(p.getName());
                    dto.setDescription(p.getDescription());
                    dto.setRetailPrice(p.getRetailPrice());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseAdminDTO> getAllProductsAdmin() {
        return productDao.findAvailableProducts()
                .stream()
                .map(p -> {
                    ProductResponseAdminDTO dto = new ProductResponseAdminDTO();
                    dto.setProductId(p.getProductId());
                    dto.setName(p.getName());
                    dto.setDescription(p.getDescription());
                    dto.setRetailPrice(p.getRetailPrice());
                    dto.setQuantity(p.getQuantity());
                    dto.setWholesalePrice(p.getWholesalePrice());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO getProductByIdUser(Long productId) {
        return productDao.fetchProductById(productId)
                .map(p -> {
                    ProductResponseDTO dto = new ProductResponseDTO();
                    dto.setProductId(p.getProductId());
                    dto.setName(p.getName());
                    dto.setDescription(p.getDescription());
                    dto.setRetailPrice(p.getRetailPrice());
                    return dto;
                })
                .orElse(null);
    }

    @Override
    public ProductResponseAdminDTO getProductByIdAdmin(Long productId) {
        return productDao.fetchProductById(productId)
                .map(p -> {
                    ProductResponseAdminDTO dto = new ProductResponseAdminDTO();
                    dto.setProductId(p.getProductId());
                    dto.setName(p.getName());
                    dto.setDescription(p.getDescription());
                    dto.setRetailPrice(p.getRetailPrice());
                    dto.setQuantity(p.getQuantity());
                    dto.setWholesalePrice(p.getWholesalePrice());
                    return dto;
                })
                .orElse(null);
    }

    @Override
    public List<ProductResponseDTO> getRecentProducts(int limit) {

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userDao.findByUsername(currentUsername)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return productDao.findRecentProducts(limit, user.getUserId())
                .stream()
                .limit(limit)
                .map(p -> {
                    ProductResponseDTO dto = new ProductResponseDTO();
                    dto.setProductId(p.getProductId());
                    dto.setName(p.getName());
                    dto.setDescription(p.getDescription());
                    dto.setRetailPrice(p.getRetailPrice());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> getFrequentProducts(int limit) {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userDao.findByUsername(currentUsername)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return productDao.findFrequentProducts(limit, user.getUserId())
                .stream()
                .limit(limit)
                .map(p -> {
                    ProductResponseDTO dto = new ProductResponseDTO();
                    dto.setProductId(p.getProductId());
                    dto.setName(p.getName());
                    dto.setDescription(p.getDescription());
                    dto.setRetailPrice(p.getRetailPrice());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void updateProduct(Long productId, ProductRequestDTO productRequestDTO ){
        Optional<Product> optionalProduct = productDao.fetchProductById(productId);
        if(!optionalProduct.isPresent()){
            throw new ProductNotFoundException("The product with provided productId was not found");
        }
        else{
            Product product = optionalProduct.get();
            product.setName(productRequestDTO.getName());
            product.setQuantity(productRequestDTO.getQuantity());
            product.setDescription(productRequestDTO.getDescription());
            product.setWholesalePrice(productRequestDTO.getWholesalePrice());
            product.setRetailPrice(productRequestDTO.getRetailPrice());

            productDao.save(product);
        }
    }

    @Override
    public void addProduct(ProductRequestDTO productRequestDTO) {
        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setQuantity(productRequestDTO.getQuantity());
        product.setDescription(productRequestDTO.getDescription());
        product.setWholesalePrice(productRequestDTO.getWholesalePrice());
        product.setRetailPrice(productRequestDTO.getRetailPrice());

        productDao.save(product);
    }

    @Override
    public List<ProductResponseAdminDTO> getTopProfitableProducts(int limit) {
        return productDao.findTopProfitableProducts(limit)
                .stream()
                .limit(limit)
                .map(p -> {
                    ProductResponseAdminDTO dto = new ProductResponseAdminDTO();
                    dto.setProductId(p.getProductId());
                    dto.setName(p.getName());
                    dto.setDescription(p.getDescription());
                    dto.setRetailPrice(p.getRetailPrice());
                    dto.setWholesalePrice(p.getWholesalePrice());
                    dto.setQuantity(p.getQuantity());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseAdminDTO> getMostPopularProducts(int limit) {
        return productDao.findMostPopularProducts(limit)
                .stream()
                .limit(limit)
                .map(p -> {
                    ProductResponseAdminDTO dto = new ProductResponseAdminDTO();
                    dto.setProductId(p.getProductId());
                    dto.setName(p.getName());
                    dto.setDescription(p.getDescription());
                    dto.setRetailPrice(p.getRetailPrice());
                    dto.setWholesalePrice(p.getWholesalePrice());
                    dto.setQuantity(p.getQuantity());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public int getTotalSoldProducts(){
        return productDao.findTotalSoldProducts();
    }
}
