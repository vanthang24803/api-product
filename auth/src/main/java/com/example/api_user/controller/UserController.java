package com.example.api_user.controller;

import com.example.api_user.entity.ERole;
import com.example.api_user.entity.Role;
import com.example.api_user.entity.Users;
import com.example.api_user.jwt.JwtTokenProvider;
import com.example.api_user.payload.request.LoginRequest;
import com.example.api_user.payload.request.SignupRequest;
import com.example.api_user.payload.respone.JwtRespone;
import com.example.api_user.payload.respone.MessageRespone;
import com.example.api_user.security.CustomUserDetail;
import com.example.api_user.service.RoleService;
import com.example.api_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    private final UserService userService;

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody SignupRequest signupRequest) {
        if (userService.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageRespone("Username is already"));
        }
        if (userService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageRespone("Email is already"));
        }
        Users user = new Users();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
        user.setFirstname(signupRequest.getFirstname());
        user.setLastname(signupRequest.getLastname());
        user.setNumberPhone(signupRequest.getNumberPhone());
        user.setUserStatus(true);
        Set<String> strRoles = new HashSet<>();
        Set<Role> listRoles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleService.findRoleByValue(ERole.ROLE_USER).orElseThrow(
                    () -> new RuntimeException("Sometime went wrong!")
            );
            listRoles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleService.findRoleByValue(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Role not found"));
                        listRoles.add(adminRole);
                    case "moderator":
                        Role moderatorRole = roleService.findRoleByValue(ERole.ROLE_MODINATOR)
                                .orElseThrow(() -> new RuntimeException("Role not found"));
                        listRoles.add(moderatorRole);
                    case "user":
                        Role userRole = roleService.findRoleByValue(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Role not found"));
                        listRoles.add(userRole);
                }
            });
        }
        user.setListRoles(listRoles);
        userService.saveOrUpdate(user);
        return ResponseEntity.ok(new MessageRespone("User Register Successfully"));
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        String jwt = jwtTokenProvider.generateToken(customUserDetail);
        List<String> listRoles = customUserDetail.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();
        return ResponseEntity.ok(
                new JwtRespone(jwt, customUserDetail.getUserId(), customUserDetail.getUsername(),
                        customUserDetail.getEmail(), customUserDetail.getFirstname(),
                        customUserDetail.getLastname(), customUserDetail.getNumberPhone(),
                        listRoles)
        );
    }

}
