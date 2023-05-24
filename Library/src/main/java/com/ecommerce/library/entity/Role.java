package com.ecommerce.library.entity;

import com.ecommerce.library.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "roles",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UK_roleName",
                        columnNames = {"name"}
                )
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum name;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Admin> admins;
}