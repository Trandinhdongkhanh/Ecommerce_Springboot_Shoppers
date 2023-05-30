package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.CustomerDTO;
import com.ecommerce.library.entity.Customer;
import com.ecommerce.library.repository.CustomerRepo;
import com.ecommerce.library.repository.RoleRepo;
import com.ecommerce.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.ecommerce.library.enums.RoleEnum.ROLE_CUSTOMER;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Override
    public Customer findByUsername(String username) {
        return customerRepo
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
    }

    @Override
    public void register(CustomerDTO customerDTO) {
        Customer customer = Mapper.toCustomer(customerDTO);
        customer.setRoles(Set.of(roleRepo.findByName(ROLE_CUSTOMER).get()));
        customerRepo.save(customer);
    }

    @Override
    public Boolean existByUsername(String username) {
        return customerRepo.existsByUsername(username);
    }

    @Override
    public Boolean existByEmail(String email) {
        return customerRepo.existsByEmail(email);
    }

    @Override
    public Boolean existByPhoneNo(String phoneNo) {
        return customerRepo.existsByPhoneNo(phoneNo);
    }
}
