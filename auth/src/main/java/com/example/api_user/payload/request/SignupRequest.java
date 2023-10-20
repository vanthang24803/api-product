package com.example.api_user.payload.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class SignupRequest {
    private String username;
    private String password;
    private String email;
    private String firstname;
    private String lastname;
    private String numberPhone;
    private Set<String> listRoles;
    private boolean userStatus;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date createAt = new Date();

}
