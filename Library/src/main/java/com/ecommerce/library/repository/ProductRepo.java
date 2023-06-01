package com.ecommerce.library.repository;

import com.ecommerce.library.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    @Query("select p from Product p")
    Page<Product> pageProduct(Pageable pageable);
    @Query("select p from Product p where p.description like %?1% or p.name like %?1%")
    Page<Product> searchProductByName(String keyword, Pageable pageable);
    @Query("select p from Product p where p.is_activated = true and p.is_deleted = false")
    List<Product> findAllByIs_activated();
    @Query(value = "select * from products p where p.is_deleted = false and p.is_activated = true order by rand() asc limit 4 ", nativeQuery = true)
    List<Product> listViewProducts();

    @Query("select p from Product p where p.category.id = ?1")
    List<Product> getRelatedProducts(Long cateId);
}
