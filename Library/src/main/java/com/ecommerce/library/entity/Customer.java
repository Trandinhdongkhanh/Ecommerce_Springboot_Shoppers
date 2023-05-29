package com.ecommerce.library.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "customers")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;
    @Column(nullable = false)
    @NotBlank
    private String fullName;
    @Column(nullable = false)
    @NotBlank
    private String username;
    @Column(nullable = false)
    @Email
    @NotBlank
    private String email;
    @Column(nullable = false)
    @NotBlank
    private String password;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String phoneNo;
    @Column(nullable = false)
    private String address;
    @Lob //Dung de tao ra cac chuoi van ban dai
    @Column(columnDefinition = "MEDIUMBLOB")
    private String avatar;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "customers_roles",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    )
    private Set<Role> roles;
}
