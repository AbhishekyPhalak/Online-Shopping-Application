package com.example.shoppingapp.daoImplementation;

import com.example.shoppingapp.daoInterface.WatchListDao;
import com.example.shoppingapp.entity.Product;
import com.example.shoppingapp.entity.User;
import com.example.shoppingapp.entity.Watchlist;
import com.example.shoppingapp.exception.WatchlistEntryNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class WatchListDaoImpl implements WatchListDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void add(User user, Product product) {
        Watchlist watchlist = new Watchlist(user, product);
        entityManager.persist(watchlist);
    }

    @Override
    @Transactional
    public void remove(Long userId, Long productId) {
        String hql = "DELETE FROM Watchlist w WHERE w.user.id = :userId AND w.product.productId = :productId";
        int rowsDeleted = entityManager.createQuery(hql)
                .setParameter("userId", userId)
                .setParameter("productId", productId)
                .executeUpdate();
        if (rowsDeleted == 0) {
            throw new WatchlistEntryNotFoundException("No watchlist entry found for userId " + userId + " and productId " + productId);
        }
    }


    @Override
    public List<Product> findInStockProductsByUserId(Long userId) {
        String hql = "SELECT w.product FROM Watchlist w WHERE w.user.id = :userId AND w.product.quantity > 0";
        return entityManager.createQuery(hql, Product.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public boolean existsByUserIdAndProductId(Long userId, Long productId) {
        String hql = "SELECT COUNT(w) FROM Watchlist w WHERE w.user.id = :userId AND w.product.productId = :productId";
        Long count = entityManager.createQuery(hql, Long.class)
                .setParameter("userId", userId)
                .setParameter("productId", productId)
                .getSingleResult();
        return count > 0;
    }
}
