package com.example.shoppingauthentification.serviceInterface;

import com.example.shoppingauthentification.dto.User.UserLoginDTO;
import com.example.shoppingauthentification.dto.User.UserLoginResponseDTO;
import com.example.shoppingauthentification.dto.User.UserRegistrationDTO;

public interface UserService {
    void registerUser(UserRegistrationDTO userRegistrationDTO);

    UserLoginResponseDTO login(UserLoginDTO userLoginDTO);
}