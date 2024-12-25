package com.eilco.ecommerce.dto;

import com.eilco.ecommerce.utils.Role;
import lombok.Data;

@Data
public class UserResponse {
    private int id;
    private String userName;
    private String email;
    private String address;
    private String phoneNumber;
    private Role role;
}