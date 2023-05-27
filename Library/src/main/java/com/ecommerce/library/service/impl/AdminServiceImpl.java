package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.AdminDTO;
import com.ecommerce.library.entity.Admin;
import com.ecommerce.library.enums.RoleEnum;
import com.ecommerce.library.repository.AdminRepo;
import com.ecommerce.library.repository.RoleRepo;
import com.ecommerce.library.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
    private final AdminRepo adminRepo;
    private final RoleRepo roleRepo;

    @Autowired
    public AdminServiceImpl(AdminRepo adminRepo, RoleRepo roleRepo) {
        this.adminRepo = adminRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public AdminDTO findByUsername(String username) {
        log.info("Finding user");
        return adminRepo.findByUsername(username)
                .map(Mapper::toAdminDTO)
                .orElseThrow(()-> new UsernameNotFoundException(String.format("User: %s not found", username)));
    }

    @Override
    public void save(AdminDTO adminDTO) {
        Admin admin = Mapper.toAdmin(adminDTO);
        admin.setRoles(Set.of(roleRepo.findByName(RoleEnum.ROLE_ADMIN).get()));
        adminRepo.save(admin);
    }

    @Override
    public boolean existsByUsername(String username) {
        return adminRepo.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return adminRepo.existsByEmail(email);
    }
}