package com.example.shoppingauthentification.daoImplementation;

import com.example.shoppingauthentification.daointerface.UserDao;
import com.example.shoppingauthentification.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    @Transactional
    public void save(User user) {
        getSession().save(user);
    }

    @Override
    @Transactional
    public Optional<User> findByUsername(String username) {
        String hql = "FROM User u WHERE u.username = :username";
        List<User> result = getSession()
                .createQuery(hql, User.class)
                .setParameter("username", username)
                .getResultList();
        return result.stream().findFirst();
    }

    @Override
    @Transactional
    public Optional<User> findByEmail(String email) {
        String hql = "FROM User u WHERE u.email = :email";
        List<User> result = getSession()
                .createQuery(hql, User.class)
                .setParameter("email", email)
                .getResultList();
        return result.stream().findFirst();
    }

    @Override
    @Transactional
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(getSession().get(User.class, id));
    }
}