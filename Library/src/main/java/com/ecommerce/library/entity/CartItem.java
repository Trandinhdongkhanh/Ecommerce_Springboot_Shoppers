package com.ecommerce.library.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "cart_item")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;
    @Column(nullable = false)
    private Integer quantity;
    @Column(nullable = false, name = "total_price")
    private Double totalPrice;
    @Column(nullable = false, name = "unit_price")
    private Double unitPrice;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id")
    private Cart cart;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;
}
