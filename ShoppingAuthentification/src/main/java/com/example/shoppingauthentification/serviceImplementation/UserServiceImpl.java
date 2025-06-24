package com.example.shoppingauthentification.serviceImplementation;

import com.example.shoppingauthentification.daointerface.UserDao;
import com.example.shoppingauthentification.dto.User.UserLoginDTO;
import com.example.shoppingauthentification.dto.User.UserLoginResponseDTO;
import com.example.shoppingauthentification.dto.User.UserRegistrationDTO;
import com.example.shoppingauthentification.entity.User;
import com.example.shoppingauthentification.exception.InvalidCredentialsException;
import com.example.shoppingauthentification.exception.UserAlreadyExistsException;
import com.example.shoppingauthentification.security.JwtProvider;
import com.example.shoppingauthentification.serviceInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public void registerUser(UserRegistrationDTO userDto) {
        // Check if username or email exists
        if (userDao.findByUsername(userDto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username already exists");
        }
        if (userDao.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already exists");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        // Set role to 0 (user)
        user.setRole(0);

        userDao.save(user);
    }

    @Override
    public UserLoginResponseDTO login(UserLoginDTO loginDTO){
        Optional<User> optionalUser = userDao.findByUsername(loginDTO.getUsername());

        if (!optionalUser.isPresent()) {
            throw new InvalidCredentialsException("Incorrect credentials, please try again.");
        }

        String encodedPassword = passwordEncoder.encode(loginDTO.getPassword());
//        System.out.println("Encoded password of input: " + encodedPassword);

        User user = optionalUser.get();
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Incorrect credentials, please try again.");
        }

        String role;
        if (user.getRole() == 1) {
            role = "admin";
        } else {
            role = "user";
        }
        String token = jwtProvider.createToken(user.getUsername(), role);

        return new UserLoginResponseDTO(token, "Login successful", role);
    }
}