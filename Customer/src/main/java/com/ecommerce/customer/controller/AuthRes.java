package com.ecommerce.customer.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRes {
    private String token;
    private final String type = "Bearer";
}
