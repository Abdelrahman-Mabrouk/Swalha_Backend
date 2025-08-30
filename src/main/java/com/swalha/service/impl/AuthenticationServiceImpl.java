package com.swalha.service.impl;

import com.swalha.dto.LoginRequest;
import com.swalha.dto.LoginResponse;
import com.swalha.entity.User;
import com.swalha.security.JwtUtil;
import com.swalha.service.AuthenticationService;
import com.swalha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserService userService;
    
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        
        User user = userService.getUserByUsername(loginRequest.getUsername());
        String token = jwtUtil.generateToken(user);
        
        return new LoginResponse(token, user.getUsername(), user.getFullName(), user.getRole());
    }
} 