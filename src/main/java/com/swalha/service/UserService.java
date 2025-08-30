package com.swalha.service;

import com.swalha.entity.User;
import com.swalha.entity.UserRole;

import java.util.List;

public interface UserService {
    
    User createUser(User user);
    
    User updateUser(Long id, User user);
    
    void deleteUser(Long id);
    
    User getUserById(Long id);
    
    User getUserByUsername(String username);
    
    List<User> getAllUsers();
    
    List<User> getUsersByRole(UserRole role);
    
    boolean existsByUsername(String username);
} 