package com.ecommerce.library.service.impl;

import com.ecommerce.library.entity.Cart;
import com.ecommerce.library.entity.CartItem;
import com.ecommerce.library.entity.Order;
import com.ecommerce.library.entity.OrderDetail;
import com.ecommerce.library.repository.CartItemRepo;
import com.ecommerce.library.repository.CartRepo;
import com.ecommerce.library.repository.OrderDetailRepo;
import com.ecommerce.library.repository.OrderRepo;
import com.ecommerce.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.ecommerce.library.enums.OrderStatus.PENDING;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private OrderDetailRepo orderDetailRepo;
    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Override
    public void saveOrder(Cart cart) {
        Order order = Order.builder()
                .orderStatus(PENDING)
                .orderDate(new Date())
                .customer(cart.getCustomer())
                .totalPrice(cart.getTotalPrices())
                .build();
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (CartItem item : cart.getCartItems()) {
            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .quantity(item.getQuantity())
                    .product(item.getProduct())
                    .unitPrice(item.getProduct().getCostPrice())
                    .totalPrice(item.getTotalPrice())
                    .build();
            orderDetailList.add(orderDetail);
            cartItemRepo.deleteCartItemById(item.getId());
        }
        order.setOrderDetailList(orderDetailList);
        order.setShippingCost(0.0);
        order.setTotalPrice(cart.getTotalPrices());

        cart.setCartItems(new HashSet<>());
        cart.setTotalItems(0);
        cart.setTotalPrices(0.0);
        cartRepo.save(cart);
        orderRepo.save(order);
    }
}
