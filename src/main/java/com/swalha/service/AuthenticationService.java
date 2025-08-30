package com.swalha.service;

import com.swalha.dto.LoginRequest;
import com.swalha.dto.LoginResponse;

public interface AuthenticationService {
    
    LoginResponse login(LoginRequest loginRequest);
} 