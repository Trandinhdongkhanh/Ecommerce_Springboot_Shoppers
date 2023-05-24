package com.ecommerce.admin.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRes {
    private String token;
    private final String tokenType = "Bearer";
}
