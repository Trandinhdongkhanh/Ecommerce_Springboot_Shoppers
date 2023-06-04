package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CustomerDTO;
import com.ecommerce.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private CustomerService cusService;

    @GetMapping("/profile")
    public String account(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/api/login";
        }
        CustomerDTO customerDTO = cusService.findByUsername(principal.getName());
        model.addAttribute("customer", customerDTO);
        return "profile";
    }

    @PostMapping("/update-infor")
    public String update(Principal principal, @ModelAttribute("customer") CustomerDTO customerDTO, RedirectAttributes ra) {
        try {
            CustomerDTO customer = cusService.findByUsername(principal.getName());

            if (cusService.existByPhoneNo(customerDTO.getPhoneNo()) && !Objects.equals(customer.getPhoneNo(), customerDTO.getPhoneNo())) {
                ra.addFlashAttribute("error", "Phone No has existed !");
                return "redirect:/api/profile";
            }
            if (cusService.existByEmail(customerDTO.getEmail()) && !Objects.equals(customer.getEmail(), customerDTO.getEmail())) {
                ra.addFlashAttribute("error", "Email has existed !");
                return "redirect:/api/profile";
            }
            if (cusService.existByUsername(customerDTO.getUsername()) && !Objects.equals(customer.getUsername(), customerDTO.getUsername())) {
                ra.addFlashAttribute("error", "Username has existed !");
                return "redirect:/api/profile";
            }

            customer.setFullName(customerDTO.getFullName());
            customer.setUsername(customerDTO.getUsername());
            customer.setPhoneNo(customerDTO.getPhoneNo());
            customer.setAddress(customerDTO.getAddress());
            customer.setCity(customerDTO.getCity());
            customer.setCountry(customerDTO.getCountry());
            customer.setEmail(customerDTO.getEmail());
            cusService.save(customer);
            ra.addFlashAttribute("error", "Update Successfully !");
        } catch (Exception ex) {
            ex.printStackTrace();
            ra.addFlashAttribute("error", "Server Error");
        }
        return "redirect:/api/profile";
    }

}
