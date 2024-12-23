package com.eilco.ecommerce.dto;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String userName;
    private String email;
    private String address;
    private String phoneNumber;
}