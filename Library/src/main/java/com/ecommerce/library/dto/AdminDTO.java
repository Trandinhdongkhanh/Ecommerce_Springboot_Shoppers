package com.ecommerce.library.dto;

import com.ecommerce.library.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Builder
@Data
public class AdminDTO implements Serializable {
    private String fullName;
    private String username;
    private String email;
    private String avatar;
    private Set<Role> roles;
}