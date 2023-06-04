package com.ecommerce.library.repository;

import com.ecommerce.library.entity.Cart;
import com.ecommerce.library.entity.CartItem;
import com.ecommerce.library.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {
    Boolean existsByCartAndProduct(Cart cart, Product product);
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);

    @Query("delete from CartItem c where c.id = ?1")
    @Modifying
    @Transactional
    void deleteCartItemById(Long cartItemId);
}
