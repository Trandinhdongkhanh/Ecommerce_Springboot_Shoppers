package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.AdminDTO;
import com.ecommerce.library.entity.Admin;

public class Mapper{
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
                .email(adminDTO.getEmail())
                .avatar(adminDTO.getAvatar())
                .roles(adminDTO.getRoles())
                .build();
    }
}