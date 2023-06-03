package com.ecommerce.customer.controller;

import com.ecommerce.customer.jwt.JwtCustomerTokenProvider;
import com.ecommerce.library.dto.CustomerDTO;
import com.ecommerce.library.entity.Customer;
import com.ecommerce.library.service.CartService;
import com.ecommerce.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/api")
public class LoginController {
    //Uncomment this if you want to use JWT authentication
//    @Autowired
//    private AuthenticationManager authManager;
//    @Autowired
//    private JwtCustomerTokenProvider jwtCustomerTokenProvider;

    //Uncomment this if you want to use JWT authentication

//    @PostMapping("/login")
//    @ResponseBody
//    public ResponseEntity<?> login(@RequestBody @Valid AuthReq authReq) throws BadCredentialsException {
//        Authentication authentication = authManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        authReq.getUsername(),
//                        authReq.getPassword()
//                )
//        );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String token = jwtCustomerTokenProvider.generateToken((Customer) authentication.getPrincipal());
//
//        return ResponseEntity.ok().body(new AuthRes(token));
//    }

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CartService cartService;

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
}
