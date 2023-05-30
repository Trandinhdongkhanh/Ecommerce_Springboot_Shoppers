package com.ecommerce.library.service;

import com.ecommerce.library.dto.CustomerDTO;
import com.ecommerce.library.entity.Customer;

import java.util.Optional;

public interface CustomerService {
    Customer findByUsername(String username);
    void register(CustomerDTO customerDTO);
    Boolean existByUsername(String username);
    Boolean existByEmail(String email);
    Boolean existByPhoneNo(String phoneNo);
}
