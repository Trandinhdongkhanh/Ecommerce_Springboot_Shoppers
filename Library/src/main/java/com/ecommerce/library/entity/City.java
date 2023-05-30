package com.ecommerce.library.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cities", uniqueConstraints = @UniqueConstraint(name = "UK_cityName", columnNames = "name"))
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long id;
    @Column(nullable = false)
    private String name;
}
