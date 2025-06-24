package com.example.shoppingapp.serviceImplementation;

import com.example.shoppingapp.daoInterface.OrderDao;
import com.example.shoppingapp.daoInterface.ProductDao;
import com.example.shoppingapp.daoInterface.UserDao;
import com.example.shoppingapp.dto.Order.*;
import com.example.shoppingapp.dto.Product.ProductResponseDTO;
import com.example.shoppingapp.entity.OrderItem;
import com.example.shoppingapp.entity.Product;
import com.example.shoppingapp.entity.User;
import com.example.shoppingapp.exception.*;
import com.example.shoppingapp.serviceInterface.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.shoppingapp.entity.Order;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    private UserDao userDao;
    private ProductDao productDao;
    private OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(UserDao userDao , ProductDao productDao, OrderDao orderDao) {
        this.userDao = userDao;
        this.productDao = productDao;
        this.orderDao = orderDao;
    }


    @Override
    @Transactional
    public void placeOrder(OrderRequestDTO orderRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDao.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setDatePlaced(LocalDateTime.now());
        order.setOrderStatus("Processing");

        List<OrderItem> orderItems = new ArrayList<>();

        for(OrderItemRequestDTO itemDTO : orderRequest.getOrder()) {
            Product product = productDao.fetchProductById(itemDTO.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            if (product.getQuantity() < itemDTO.getQuantity()) {
                throw new NotEnoughInventoryException("Not enough inventory for product: " + product.getName());
            }

            product.setQuantity(product.getQuantity() - itemDTO.getQuantity());

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setOrder(order);
            item.setPurchasedPrice(product.getRetailPrice());
            item.setWholesalePrice(product.getWholesalePrice());

            orderItems.add(item);
        }
        order.setOrderItems(orderItems);
        orderDao.save(order);
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderDao.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities()
                .stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !order.getUser().getUsername().equals(currentUsername)) {
            throw new UnauthorizedActionException("You are not authorized to modify this order.");
        }

        String currentStatus = order.getOrderStatus();

        if (currentStatus.equals("Canceled")) {
            throw new OrderStatusInvalidException("Order is already canceled.");
        }

        if (currentStatus.equals("Completed")) {
            throw new OrderStatusInvalidException("Completed orders cannot be canceled.");
        }

        // User can only cancel if order is still processing
        if (!isAdmin && !currentStatus.equals("Processing")) {
            throw new OrderStatusInvalidException("Users can only cancel orders in 'Processing' state.");
        }

        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() + item.getQuantity());
            productDao.save(product);
        }

        order.setOrderStatus("Canceled");
        orderDao.update(order);
    }

    @Override
    public List<OrderResponseDTO> getOrdersForCurrentUser(int page, int size) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        List<Order> orders;

        if (isAdmin) {
            // Apply pagination for admin
            Pageable pageable = PageRequest.of(page, size);
            orders = orderDao.findAllOrders(pageable);

        } else {
            User user = userDao.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            // No pagination for normal users
            orders = orderDao.findByUserId(user.getUserId());
        }

        return orders.stream()
                .map(order -> new OrderResponseDTO(
                        order.getOrderId(),
                        order.getDatePlaced(),
                        order.getOrderStatus()))
                .collect(Collectors.toList());
    }


    @Override
    public OrderDetailResponseDTO getOrderDetails(Long orderId) {
        Order order = orderDao.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !order.getUser().getUsername().equals(currentUsername)) {
            throw new UnauthorizedActionException("You are not authorized to access this order.");
        }

        if (isAdmin) {
            List<AdminOrderDetailResponseDTO.AdminOrderItemDTO> itemDTOs = order.getOrderItems().stream()
                    .map(item -> new AdminOrderDetailResponseDTO.AdminOrderItemDTO(
                            item.getItemId(),
                            item.getProduct().getProductId(),
                            item.getProduct().getName(),
                            item.getQuantity(),
                            item.getPurchasedPrice(),
                            item.getWholesalePrice()))
                    .collect(Collectors.toList());

            return new AdminOrderDetailResponseDTO(
                    order.getOrderId(),
                    order.getDatePlaced(),
                    order.getOrderStatus(),
                    itemDTOs,
                    order.getUser().getUserId(),
                    order.getUser().getUsername()
            );
        } else {
            List<OrderDetailResponseDTO.OrderItemDTO> itemDTOs = order.getOrderItems().stream()
                    .map(item -> new OrderDetailResponseDTO.OrderItemDTO(
                            item.getItemId(),
                            item.getProduct().getProductId(),
                            item.getProduct().getName(),
                            item.getQuantity(),
                            item.getPurchasedPrice()))
                    .collect(Collectors.toList());

            return new OrderDetailResponseDTO(
                    order.getOrderId(),
                    order.getDatePlaced(),
                    order.getOrderStatus(),
                    itemDTOs
            );
        }
    }


    @Override
    @Transactional
    public void completeOrder(Long orderId) {
        Order order = orderDao.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        String status = order.getOrderStatus();

        if (status.equals("Completed")) {
            throw new OrderStatusInvalidException("Order is already completed.");
        }

        if (status.equals("Canceled")) {
            throw new OrderStatusInvalidException("Canceled orders cannot be completed.");
        }

        if (!status.equals("Processing")) {
            throw new OrderStatusInvalidException("Only orders in 'Processing' status can be completed.");
        }

        order.setOrderStatus("Completed");
        orderDao.update(order);
    }

}
