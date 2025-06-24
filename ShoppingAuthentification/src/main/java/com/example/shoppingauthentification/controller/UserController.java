package com.example.shoppingauthentification.controller;

import com.example.shoppingauthentification.dto.User.ApiResponse;
import com.example.shoppingauthentification.dto.User.UserLoginDTO;
import com.example.shoppingauthentification.dto.User.UserLoginResponseDTO;
import com.example.shoppingauthentification.dto.User.UserRegistrationDTO;
import com.example.shoppingauthentification.serviceImplementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> registerUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        userService.registerUser(userRegistrationDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "User registered successfully", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserLoginResponseDTO>> loginUser(@Valid @RequestBody UserLoginDTO loginDTO) {
        UserLoginResponseDTO loginResponse = userService.login(loginDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", loginResponse));
    }
}