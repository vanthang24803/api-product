package com.example.api_user.service;

import com.example.api_user.entity.ERole;
import com.example.api_user.entity.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findRoleByValue(ERole roleValue);
}
