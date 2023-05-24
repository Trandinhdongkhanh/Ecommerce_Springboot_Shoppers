package com.ecommerce.admin.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthReq {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
