package com.ecommerce.library.service;

import com.ecommerce.library.dto.ProductDTO;
import com.ecommerce.library.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAll();
    Product save(MultipartFile imageProduct, ProductDTO productDTO);
    Product update(ProductDTO productDTO);
    void deleteById(Long id);
    void enableById(Long id);
}
