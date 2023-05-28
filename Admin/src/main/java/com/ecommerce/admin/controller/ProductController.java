package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.ProductDTO;
import com.ecommerce.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;
    @GetMapping("/products")
    public String getAllProducts(Model model){
        List<ProductDTO> productDTOS = productService.findAll();
        model.addAttribute("products", productDTOS);
        model.addAttribute("size", productDTOS.size());
        return "products";
    }

    @GetMapping("/add-product")
    public String addProductForm(Model model){
        model.addAttribute("product", new ProductDTO());
        return "add-product";
    }
}
