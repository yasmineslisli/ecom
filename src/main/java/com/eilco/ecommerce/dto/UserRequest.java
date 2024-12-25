package com.eilco.ecommerce.dto;

import com.eilco.ecommerce.utils.Role;
import lombok.Data;

@Data
public class UserRequest {
    private String userName;
    private String email;
    private String password;
    private String address;
    private String phoneNumber;
    private Role role;

}