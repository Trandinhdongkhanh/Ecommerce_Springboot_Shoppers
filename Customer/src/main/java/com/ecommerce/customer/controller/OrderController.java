package com.ecommerce.customer.controller;

import com.ecommerce.customer.email.EmailService;
import com.ecommerce.library.dto.CustomerDTO;
import com.ecommerce.library.entity.Cart;
import com.ecommerce.library.entity.Order;
import com.ecommerce.library.service.CustomerService;
import com.ecommerce.library.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private EmailService emailService;


    @GetMapping("/check-out")
    public String checkout(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/api/login";
        }
        String username = principal.getName();
        CustomerDTO customer = customerService.findByUsername(username);
        model.addAttribute("customer", customer);
        Cart cart = customer.getCart();
        model.addAttribute("cart", cart);
        return "checkout";
    }


    @GetMapping("/order")
    public String order(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/api/login";
        }
        CustomerDTO customerDTO = customerService.findByUsername(principal.getName());
        List<Order> orders = customerDTO.getOrders();
        model.addAttribute("orders", orders);
        return "order";
    }

    @GetMapping("/save-order")
    public String saveOrder(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/api/login";
        }
        CustomerDTO customerDTO = customerService.findByUsername(principal.getName());
        Cart cart = customerDTO.getCart();
        emailService.sendEmail(customerDTO);
        orderService.saveOrder(cart);
        return "redirect:/api/order";
    }
}
