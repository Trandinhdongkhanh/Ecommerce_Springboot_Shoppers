package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.AdminDTO;
import com.ecommerce.library.entity.Admin;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Mapper{
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public static AdminDTO toAdminDTO(Admin admin) {
        return AdminDTO.builder()
                .fullName(admin.getFullName())
                .username(admin.getUsername())
                .email(admin.getEmail())
                .avatar(admin.getAvatar())
                .roles(admin.getRoles())
                .build();
    }

    public static Admin toAdmin(AdminDTO adminDTO) {
        return Admin.builder()
                .fullName(adminDTO.getFullName())
                .username(adminDTO.getUsername())
                .password(passwordEncoder.encode(adminDTO.getPassword()))
                .email(adminDTO.getEmail())
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .avatar(null)
                .build();
    }
}