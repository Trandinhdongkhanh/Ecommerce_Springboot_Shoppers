package com.ecommerce.library.repository;

import com.ecommerce.library.entity.Role;
import com.ecommerce.library.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    @Query("select r from Role r where r.name = ?1")
    Optional<Role> findByName(RoleEnum name);
}