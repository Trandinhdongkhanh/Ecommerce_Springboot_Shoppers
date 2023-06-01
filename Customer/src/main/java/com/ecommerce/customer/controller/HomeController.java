package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.ProductDTO;
import com.ecommerce.library.entity.Category;
import com.ecommerce.library.entity.Product;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api")
public class HomeController {
    @Autowired
    private CategoryService cateService;
    @Autowired
    private ProductService productService;
    @GetMapping("/home")
    public String homePage(Model model){
        List<Category> categories = cateService.findAllByActivated();
        List<ProductDTO> products = productService.findAllByActivated();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        return "index";
    }
}
