package com.example.api_user.repository;

import com.example.api_user.entity.ERole;
import com.example.api_user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
   Optional<Role> findRoleByValue(ERole roleValue);
}
