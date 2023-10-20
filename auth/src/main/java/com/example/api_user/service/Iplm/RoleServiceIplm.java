package com.example.api_user.service.Iplm;

import com.example.api_user.entity.ERole;
import com.example.api_user.entity.Role;
import com.example.api_user.repository.RoleRepository;
import com.example.api_user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceIplm implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceIplm(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<Role> findRoleByValue(ERole roleValue) {
        return roleRepository.findRoleByValue(roleValue);
    }
}
