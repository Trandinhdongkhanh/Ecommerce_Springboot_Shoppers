package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.CustomerDTO;
import com.ecommerce.library.dto.ProductDTO;
import com.ecommerce.library.entity.CartItem;
import com.ecommerce.library.entity.Product;
import com.ecommerce.library.entity.Cart;
import com.ecommerce.library.repository.CartItemRepo;
import com.ecommerce.library.repository.CartRepo;
import com.ecommerce.library.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartItemRepo itemRepo;

    @Autowired
    private CartRepo cartRepo;

    @Override
    public Cart addItemToCart(ProductDTO productDTO, int quantity, CustomerDTO customerDTO) {
        Cart cart = customerDTO.getCart();
        Set<CartItem> cartItems = cart.getCartItems();
        if (!itemRepo.existsByCartAndProduct(customerDTO.getCart(), Mapper.toProduct(productDTO))) {
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * productDTO.getCostPrice())
                    .cart(customerDTO.getCart())
                    .product(Mapper.toProduct(productDTO))
                    .build();
            cartItems.add(cartItem);
            itemRepo.save(cartItem);
        } else {
            Optional<CartItem> cartItem = itemRepo.findByCartIdAndProductId(customerDTO.getCart().getId(), productDTO.getId());
            if (cartItem.isPresent()) {
                cartItem.get().setQuantity(cartItem.get().getQuantity() + quantity);
                cartItem.get().setTotalPrice(cartItem.get().getTotalPrice() + (quantity * productDTO.getCostPrice()));
                cartItems.add(cartItem.get());
                itemRepo.save(cartItem.get());
            }
        }

        cart.setCartItems(cartItems);

        int totalItems = totalItems(cart.getCartItems());
        double totalPrice = totalPrice(cart.getCartItems());

        cart.setTotalPrices(totalPrice);
        cart.setTotalItems(totalItems);

        return cartRepo.save(cart);
    }

    @Override
    public Cart updateItemInCart(ProductDTO productDTO, int quantity, CustomerDTO customer) {
        Cart cart = customer.getCart();

        Set<CartItem> cartItems = cart.getCartItems();

        CartItem item = findCartItem(cartItems, productDTO.getId());

        item.setQuantity(quantity);
        item.setTotalPrice(quantity * productDTO.getCostPrice());

        itemRepo.save(item);

        int totalItems = totalItems(cartItems);
        double totalPrice = totalPrice(cartItems);

        cart.setCartItems(cartItems);
        cart.setTotalItems(totalItems);
        cart.setTotalPrices(totalPrice);

        return cartRepo.save(cart);
    }

    @Override
    public Cart deleteItemFromCart(ProductDTO productDTO, CustomerDTO customer) {
        Cart cart = customer.getCart();

        Set<CartItem> cartItems = cart.getCartItems();

        CartItem item = findCartItem(cartItems, productDTO.getId());

        cartItems.remove(item);

        itemRepo.delete(item);

        double totalPrice = totalPrice(cartItems);
        int totalItems = totalItems(cartItems);

        cart.setCartItems(cartItems);
        cart.setTotalItems(totalItems);
        cart.setTotalPrices(totalPrice);

        return cartRepo.save(cart);
    }

    @Override
    public void save(CustomerDTO customerDTO) {
        try {
            Cart cart = Cart.builder()
                    .totalItems(null)
                    .totalPrices(null)
                    .customer(Mapper.toCustomer(customerDTO))
                    .build();
            cartRepo.save(cart);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private CartItem findCartItem(Set<CartItem> cartItems, Long productId) {
        if (cartItems == null) {
            return null;
        }
        CartItem cartItem = null;

        for (CartItem item : cartItems) {
            if (item.getProduct().getId().equals(productId)) {
                cartItem = item;
            }
        }
        return cartItem;
    }

    private int totalItems(Set<CartItem> cartItems) {
        int totalItems = 0;
        for (CartItem item : cartItems) {
            totalItems += item.getQuantity();
        }
        return totalItems;
    }

    private double totalPrice(Set<CartItem> cartItems) {
        double totalPrice = 0.0;

        for (CartItem item : cartItems) {
            totalPrice += item.getTotalPrice();
        }

        return totalPrice;
    }
}
