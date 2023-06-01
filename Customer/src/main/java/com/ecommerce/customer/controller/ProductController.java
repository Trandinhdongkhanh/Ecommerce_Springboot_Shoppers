package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.ProductDTO;
import com.ecommerce.library.entity.Category;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService cateService;
    @GetMapping("/products")
    public String getAllProducts(Model model){
        List<ProductDTO> productDTOS = productService.findAllByActivated();
        List<Category> categories = cateService.findAllByActivated();
        List<ProductDTO> listViewProducts = productService.listViewProducts();
        model.addAttribute("products", productDTOS);
        model.addAttribute("categories", categories);
        model.addAttribute("viewProducts", listViewProducts);
        return "shop";
    }

    @GetMapping("/find-product/{id}")
    public String getProductDetail(@PathVariable("id") Long id, Model model){
        ProductDTO product = productService.findById(id);
        List<ProductDTO> relatedProducts = productService.getRelatedProducts(product.getCategory().getId());
        model.addAttribute("product", product);
        model.addAttribute("relatedProducts", relatedProducts);
        return "product-detail";
    }
}
