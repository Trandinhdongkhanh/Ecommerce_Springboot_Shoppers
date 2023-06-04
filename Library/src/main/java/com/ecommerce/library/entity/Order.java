package com.ecommerce.library.entity;

import com.ecommerce.library.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "orders")

public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    @Column(nullable = false, name = "oder_date")
    private Date orderDate;
    @Column(nullable = true, name = "delivery_date")
    private Date deliveryDate;
    @Column(nullable = false, name = "total_price")
    private Double totalPrice;
    @Column(nullable = false, name = "shipping_cost")
    private Double shippingCost;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "order_status")
    private OrderStatus orderStatus;
    private String notes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
    private List<OrderDetail> orderDetailList;
}
