package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.AdminDTO;
import com.ecommerce.library.dto.CustomerDTO;
import com.ecommerce.library.dto.ProductDTO;
import com.ecommerce.library.entity.Admin;
import com.ecommerce.library.entity.Customer;
import com.ecommerce.library.entity.Product;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;


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
    public static ProductDTO toProductDTO(Product product){
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .costPrice(product.getCostPrice())
                .salePrice(product.getSalePrice())
                .currentQuantity(product.getCurrentQuantity())
                .image(product.getImage())
                .category(product.getCategory())
                .is_deleted(product.getIs_deleted())
                .is_activated(product.getIs_activated())
                .build();
    }

    public static CustomerDTO toCustomerDTO(Customer customer){
        return CustomerDTO.builder()
                .fullName(customer.getFullName())
                .username(customer.getUsername())
                .password(customer.getPassword())
                .email(customer.getEmail())
                .country(customer.getCountry())
                .city(customer.getCity())
                .phoneNo(customer.getPhoneNo())
                .address(customer.getAddress())
                .avatar(customer.getAvatar())
                .roles(customer.getRoles())
                .cart(customer.getCart())
                .build();
    }
    public static Customer toCustomer(CustomerDTO customerDTO){
        return Customer.builder()
                .fullName(customerDTO.getFullName())
                .username(customerDTO.getUsername())
                .password(passwordEncoder.encode(customerDTO.getPassword()))
                .email(customerDTO.getEmail())
                .country(customerDTO.getCountry())
                .city(customerDTO.getCity())
                .phoneNo(customerDTO.getPhoneNo())
                .address(customerDTO.getAddress())
                .avatar(null)
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .build();
    }
}