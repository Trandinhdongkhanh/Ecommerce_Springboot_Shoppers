package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.CustomerDTO;
import com.ecommerce.library.entity.Cart;
import com.ecommerce.library.entity.Customer;
import com.ecommerce.library.repository.CustomerRepo;
import com.ecommerce.library.repository.RoleRepo;
import com.ecommerce.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.ecommerce.library.enums.RoleEnum.ROLE_CUSTOMER;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private RoleRepo roleRepo;

    @Override
    public CustomerDTO findByUsername(String username) {
        return customerRepo
                .findByUsername(username)
                .map(customer -> Mapper.getInstance().toCustomerDTO(customer))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found", username)));
    }

    @Override
    public void register(CustomerDTO customerDTO) {
        Customer customer = Mapper.getInstance().toCustomer(customerDTO);
        customer.setRoles(Set.of(roleRepo.findByName(ROLE_CUSTOMER).get()));
        customer.setCart(Cart.builder()
                .totalItems(0)
                .totalPrices(0.0)
                .cartItems(new HashSet<>())
                .customer(customer)
                .build());
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

    @Override
    public void save(CustomerDTO customerDTO) {
        Customer customer = customerRepo.findById(customerDTO.getId()).get();
        customer.setFullName(customerDTO.getFullName());
        customer.setUsername(customerDTO.getUsername());
        customer.setEmail(customerDTO.getEmail());
        customer.setCountry(customerDTO.getCountry());
        customer.setCity(customerDTO.getCity());
        customer.setPhoneNo(customer.getPhoneNo());
        customer.setAddress(customerDTO.getAddress());
        customerRepo.save(customer);
    }
}
