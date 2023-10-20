package com.example.api_user.service;

import com.example.api_user.entity.Users;

public interface UserService {
    Users findUsersByUsername(String username);
    boolean existsByUsername(String userName);
    boolean existsByEmail(String email);
    Users saveOrUpdate(Users user);
}
