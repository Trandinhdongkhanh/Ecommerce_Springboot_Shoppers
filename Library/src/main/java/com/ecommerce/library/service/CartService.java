package com.ecommerce.library.service;

import com.ecommerce.library.dto.CustomerDTO;
import com.ecommerce.library.dto.ProductDTO;
import com.ecommerce.library.entity.Cart;

public interface CartService {
    Cart addItemToCart(ProductDTO productDTO, int quantity, CustomerDTO customer);

    Cart updateItemInCart(ProductDTO productDTO, int quantity, CustomerDTO customer);

    Cart deleteItemFromCart(ProductDTO productDTO, CustomerDTO customer);
    void save(CustomerDTO customerDTO);
}
