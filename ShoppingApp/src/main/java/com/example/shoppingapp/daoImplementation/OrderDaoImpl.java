package com.example.shoppingapp.daoImplementation;

import com.example.shoppingapp.daoInterface.OrderDao;
import com.example.shoppingapp.entity.Order;
import com.example.shoppingapp.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Order order) {
        entityManager.persist(order);
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        return Optional.ofNullable(entityManager.find(Order.class, orderId));
    }

    @Override
    @Transactional
    public void update(Order order) {
        entityManager.merge(order);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        String hql = "FROM Order o WHERE o.user.userId = :userId ORDER BY o.datePlaced DESC";
        return entityManager.createQuery(hql, Order.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Order> findAllOrders(Pageable pageable) {
        String hql = "FROM Order o ORDER BY o.datePlaced DESC";
        return entityManager.createQuery(hql, Order.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

}
