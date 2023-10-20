package com.example.api_user.service.Iplm;

import com.example.api_user.entity.Users;
import com.example.api_user.repository.UserRepository;
import com.example.api_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceIplm implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceIplm(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Users findUsersByUsername(String username) {
        return userRepository.findUsersByUsername(username);
    }

    @Override
    public boolean existsByUsername(String userName) {
        return userRepository.existsByUsername(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Users saveOrUpdate(Users user) {
        return userRepository.save(user);
    }
}
