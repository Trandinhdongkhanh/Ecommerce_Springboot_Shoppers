package com.ecommerce.library.service;

import com.ecommerce.library.dto.ProductDTO;
import com.ecommerce.library.entity.Product;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAll();
    Product save(ProductDTO product);
    Product update(ProductDTO product);
    void deleteById(Long id);
    void enableById(Long id);
}
