package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CustomerDTO;
import com.ecommerce.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("customerDTO", new CustomerDTO());
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@ModelAttribute("customerDTO") CustomerDTO customerDTO, RedirectAttributes ra) {
        try {
            if (customerService.existByUsername(customerDTO.getUsername())) {
                ra.addFlashAttribute("message", "Username existed !");
                return "redirect:/api/register";
            }
            if (customerService.existByEmail(customerDTO.getEmail())) {
                ra.addFlashAttribute("message", "Email existed !");
                return "redirect:/api/register";
            }
            if (customerService.existByPhoneNo(customerDTO.getPhoneNo())) {
                ra.addFlashAttribute("message", "Phone number existed !");
                return "redirect:/api/register";
            }
            customerService.register(customerDTO);
        } catch (Exception ex) {
            ex.printStackTrace();
            ra.addFlashAttribute("message", "Server Error!");
        }
        return "redirect:/api/login";
    }

    @GetMapping("/home")
    public String homePage() {
        return "index";
    }
}
