package com.ecommerce.library.service;

import com.ecommerce.library.dto.ProductDTO;
import com.ecommerce.library.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Page<ProductDTO> searchProductByName(String keyword, int pageNo);
    Page<ProductDTO> pageProduct(int pageNo);
    List<ProductDTO> findAll();
    Product save(MultipartFile imageProduct, ProductDTO productDTO);
    Product update(MultipartFile imageProduct, ProductDTO productDTO);
    void deleteById(Long id);
    void enableById(Long id);
    ProductDTO findById(Long id);
    void deletePermanently(Long id);
}
