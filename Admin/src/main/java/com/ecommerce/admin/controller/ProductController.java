package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.ProductDTO;
import com.ecommerce.library.entity.Category;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService cateService;

    @GetMapping("/products")
    public String getAllProducts(Model model) {
        List<ProductDTO> productDTOS = productService.findAll();
        model.addAttribute("products", productDTOS);
        model.addAttribute("size", productDTOS.size());
        return "products";
    }

    @GetMapping("/add-product")
    public String addProductForm(Model model) {
        List<Category> categories = cateService.findAllByActivated();
        model.addAttribute("categories", categories);
        model.addAttribute("product", new ProductDTO());
        return "add-product";
    }

    @PostMapping("/save-product")
    public String saveProduct(
            @ModelAttribute("product") ProductDTO productDTO,
            @RequestParam("imageProduct") MultipartFile imageProduct,
            RedirectAttributes ra
    ) {
        try {
            productService.save(imageProduct, productDTO);
            ra.addFlashAttribute("success", "Add successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            ra.addFlashAttribute("error", "Failed to add!");
        }
        return "redirect:/api/products";
    }
}
