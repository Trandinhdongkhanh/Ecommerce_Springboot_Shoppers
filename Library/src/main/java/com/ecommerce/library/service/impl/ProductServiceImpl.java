package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.ProductDTO;
import com.ecommerce.library.entity.Product;
import com.ecommerce.library.repository.ProductRepo;
import com.ecommerce.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Override
    public List<ProductDTO> findAll() {
        List<ProductDTO> productDTOS = new ArrayList<>();
        productRepo.findAll().forEach(product -> productDTOS.add(Mapper.toProductDTO(product)));
        return productDTOS;
    }

    @Override
    public Product save(ProductDTO product) {
        return null;
    }

    @Override
    public Product update(ProductDTO product) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void enableById(Long id) {

    }
}
