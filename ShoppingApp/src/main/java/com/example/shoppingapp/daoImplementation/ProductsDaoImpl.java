package com.example.shoppingapp.daoImplementation;

import com.example.shoppingapp.daoInterface.ProductDao;
import com.example.shoppingapp.entity.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductsDaoImpl implements ProductDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> findAvailableProducts() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> productRoot = cq.from(Product.class);

        cq.select(productRoot).where(cb.gt(productRoot.get("quantity"), 0));

        return entityManager.createQuery(cq).getResultList();
    }

    @Override
    public Optional<Product> fetchProductById(Long productId) {
        Product product =  entityManager.find(Product.class, productId);
        return Optional.ofNullable(product);
    }

    @Override
    public List<Product> findRecentProducts(int limit, Long userId) {
        String hql = "SELECT p " +
                "FROM com.example.shoppingapp.entity.Order o " +
                "JOIN o.orderItems oi " +
                "JOIN oi.product p " +
                "WHERE o.user.userId = :userId AND o.orderStatus = 'Completed' " +
                "ORDER BY o.datePlaced DESC, oi.itemId DESC";

        return entityManager.createQuery(hql, Product.class)
                .setParameter("userId", userId)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Product> findFrequentProducts(int limit, Long userId) {
        String hql = "SELECT p " +
                "FROM com.example.shoppingapp.entity.Order o " +
                "JOIN o.orderItems oi " +
                "JOIN oi.product p " +
                "WHERE o.user.userId = :userId AND o.orderStatus = 'Completed' " +
                "GROUP BY p.productId " +
                "ORDER BY COUNT(oi.itemId) DESC";

        return entityManager.createQuery(hql, Product.class)
                .setParameter("userId", userId)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    @Transactional
    public void save(Product product){
        entityManager.persist(product);
    }

    @Override
    @Transactional
    public List<Product> findTopProfitableProducts(int limit) {
        String hql = "SELECT p " +
                "FROM Order o " +
                "JOIN o.orderItems oi " +
                "JOIN oi.product p " +
                "WHERE o.orderStatus = 'Completed' " +
                "GROUP BY p.productId " +
                "ORDER BY SUM(oi.purchasedPrice - oi.wholesalePrice) DESC";
        return entityManager.createQuery(hql, Product.class)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    @Transactional
    public List<Product> findMostPopularProducts(int limit) {
        String hql = "SELECT p " +
                "FROM Order o " +
                "JOIN o.orderItems oi " +
                "JOIN oi.product p " +
                "WHERE o.orderStatus = 'Completed' " +
                "GROUP BY p.productId " +
                "ORDER BY SUM(oi.quantity) DESC";
        return entityManager.createQuery(hql, Product.class)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    @Transactional
    public int findTotalSoldProducts() {
        String hql = "SELECT SUM(oi.quantity) " +
                "FROM Order o " +
                "JOIN o.orderItems oi " +
                "WHERE o.orderStatus = 'Completed'";
        Long total = (Long) entityManager.createQuery(hql).getSingleResult();
        return total != null ? total.intValue() : 0;
    }
}
