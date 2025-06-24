package com.example.shoppingapp.daoInterface;

import com.example.shoppingapp.entity.User;

import java.util.Optional;

public interface UserDao {

    void save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);
}
