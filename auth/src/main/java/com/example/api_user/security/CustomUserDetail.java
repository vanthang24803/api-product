package com.example.api_user.security;

import com.example.api_user.entity.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor

public class CustomUserDetail implements UserDetails {
    private Long userId;
    private String username;
    @JsonIgnore
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String numberPhone;
    private String imageUrl;
    private boolean userStatus;
    private Date createAt;
    private Date updateAt;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public static CustomUserDetail mapUserToUserDetail(Users user){
        List<GrantedAuthority> authorityList = user.getListRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getValue().name())).collect(Collectors.toList());
        return new CustomUserDetail(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getNumberPhone(),
                user.getImageUrl(),
                user.isUserStatus(),
                user.getCreateAt(),
                user.getUpadateAt(),
                authorityList
        );
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public long getId() {
        return this.userId;
    }
}
