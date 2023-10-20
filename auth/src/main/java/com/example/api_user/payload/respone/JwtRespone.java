package com.example.api_user.payload.respone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRespone {
    private String token;
    private String type = "Bearer ";
    private Long  userId;
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private String numberPhone;
    private List<String> listRoles;

    public JwtRespone(String token, Long userId, String username, String email, String firstname,
                      String lastname, String numberPhone, List<String> listRoles) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.numberPhone = numberPhone;
        this.listRoles = listRoles;
    }
}
