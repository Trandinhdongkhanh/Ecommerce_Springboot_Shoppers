package com.ecommerce.library.service;

import com.ecommerce.library.dto.AdminDTO;

public interface AdminService {
    AdminDTO findByUsername(String username);
    void save(AdminDTO adminDTO);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
