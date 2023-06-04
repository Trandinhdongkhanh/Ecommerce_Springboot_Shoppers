package com.ecommerce.library.service;

import com.ecommerce.library.entity.Cart;

public interface OrderService {
    void saveOrder(Cart cart);
}
