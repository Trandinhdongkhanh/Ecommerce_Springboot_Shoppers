package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CustomerDTO;
import com.ecommerce.library.dto.ProductDTO;
import com.ecommerce.library.entity.Cart;
import com.ecommerce.library.entity.Category;
import com.ecommerce.library.entity.Product;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/api")
public class HomeController {
    @Autowired
    private CategoryService cateService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService cusService;
    @GetMapping("/home")
    public String homePage(Model model, Principal principal, HttpSession session){
        List<Category> categories = cateService.findAllByActivated();
        List<ProductDTO> products = productService.findAllByActivated();
        model.addAttribute("categories", categories);
        model.addAttribute("products", products);
        if(principal != null){
            session.setAttribute("username", principal.getName());
            CustomerDTO customer = cusService.findByUsername(principal.getName());
//            Cart cart = customer.getCart();
//            session.setAttribute("totalItems", cart.getTotalItems());
        }else{
            session.removeAttribute("username");
        }
        return "index";
    }
}
