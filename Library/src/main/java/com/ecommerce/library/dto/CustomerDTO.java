package com.ecommerce.library.dto;

import com.ecommerce.library.entity.Cart;
import com.ecommerce.library.entity.Order;
import com.ecommerce.library.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private Long id;
    @Size(min = 3, max = 20, message = "Invalid name!(3-20 characters")
    private String fullName;
    @Size(min = 3, max = 20, message = "Invalid username!(3-20 characters")
    private String username;
    @Size(min = 3, max = 20, message = "Invalid password!(3-20 characters")
    private String password;
    @Email(message = "Invalid Email!")
    private String email;
    private String country;
    private String city;
    @Size(min = 3, max = 20, message = "Invalid password!(3-20 characters")
    private String phoneNo;
    private String address;
    private String avatar;
    private Set<Role> roles;
    private Cart cart;
    private List<Order> orders;
}
