package com.ecommerce.library.repository;

import com.ecommerce.library.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    @Query("select c from Customer c where c.username = ?1")
    Optional<Customer> findByUsername(String username);
}
