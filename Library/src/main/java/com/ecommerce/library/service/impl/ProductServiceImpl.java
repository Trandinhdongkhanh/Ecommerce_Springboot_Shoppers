package com.ecommerce.library.service.impl;

import com.ecommerce.library.dto.ProductDTO;
import com.ecommerce.library.entity.Product;
import com.ecommerce.library.repository.ProductRepo;
import com.ecommerce.library.service.ProductService;
import com.ecommerce.library.utils.ImageUpload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ImageUpload imageUpload;
    @Autowired
    private ProductRepo productRepo;

    @Override
    public List<ProductDTO> findAll() {
        List<ProductDTO> productDTOS = new ArrayList<>();
        productRepo.findAll().forEach(product -> productDTOS.add(Mapper.toProductDTO(product)));
        return productDTOS;
    }

    @Override
    public Product save(MultipartFile imageProduct, ProductDTO productDTO) {
        try {
            Product product = Product.builder()
                    .name(productDTO.getName())
                    .description(productDTO.getDescription())
                    .category(productDTO.getCategory())
                    .costPrice(productDTO.getCostPrice())
                    .currentQuantity(productDTO.getCurrentQuantity())
                    .is_activated(true)
                    .is_deleted(false)
                    .build();
            if (imageProduct == null) {
                product.setImage(null);
            } else {
                if (imageUpload.uploadImg(imageProduct)) {
                    log.info("Upload Successfully");
                }
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            return productRepo.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Product update(MultipartFile imageProduct, ProductDTO productDTO) {
        try {
            Product product = productRepo.findById(productDTO.getId()).get();
            if (imageProduct == null) {
                product.setImage(product.getImage());
            } else {
                if (!imageUpload.isExisted(imageProduct)) { //If the img is not existed, we upload it to the folder
                    imageUpload.uploadImg(imageProduct);
                }
                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
            }
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setSalePrice(productDTO.getSalePrice());
            product.setCostPrice(productDTO.getCostPrice());
            product.setCurrentQuantity(productDTO.getCurrentQuantity());
            product.setCategory(productDTO.getCategory());
            return productRepo.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void enableById(Long id) {

    }

    @Override
    public ProductDTO findById(Long id) {
        Optional<ProductDTO> productDTO = productRepo.findById(id).map(Mapper::toProductDTO);
        return productDTO.orElse(null);
    }
}
