package com.example.api_user.repository;

import com.example.api_user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findUsersByUsername(String username);
    boolean existsByUsername(String userName);
    boolean existsByEmail(String email);
}
