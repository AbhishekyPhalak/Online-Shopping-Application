package com.example.shoppingauthentification.daointerface;

import com.example.shoppingauthentification.entity.User;

import java.util.Optional;

public interface UserDao {

    void save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
}
