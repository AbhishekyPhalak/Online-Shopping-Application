package com.example.shoppingapp.serviceImplementation;

import com.example.shoppingapp.daoInterface.ProductDao;
import com.example.shoppingapp.daoInterface.UserDao;
import com.example.shoppingapp.daoInterface.WatchListDao;
import com.example.shoppingapp.dto.Product.ProductResponseDTO;
import com.example.shoppingapp.entity.Product;
import com.example.shoppingapp.entity.User;
import com.example.shoppingapp.exception.ProductAlreadyExistsInWatchlist;
import com.example.shoppingapp.exception.ProductNotFoundException;
import com.example.shoppingapp.exception.UserNotFoundException;
import com.example.shoppingapp.serviceInterface.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WatchListServiceImpl implements WatchListService {

    private final UserDao userDao;
    private final ProductDao productDao;
    private final WatchListDao watchlistDao;

    @Autowired
    public WatchListServiceImpl(UserDao userDao, ProductDao productDao, WatchListDao watchlistDao) {
        this.userDao = userDao;
        this.productDao = productDao;
        this.watchlistDao = watchlistDao;
    }

    @Override
    public void addToWatchlist(Long productId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Product product = productDao.fetchProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        boolean exists = watchlistDao.existsByUserIdAndProductId(user.getUserId(), productId);
        if (exists) {
            throw new ProductAlreadyExistsInWatchlist("Product already in watchlist for user");
        }

        watchlistDao.add(user, product);
    }

    @Override
    public void removeFromWatchlist(Long productId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Product product = productDao.fetchProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        watchlistDao.remove(user.getUserId(), productId);
    }

    @Override
    public List<ProductResponseDTO> getWatchlist() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        List<Product> products = watchlistDao.findInStockProductsByUserId(user.getUserId());

        return products.stream()
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
}

