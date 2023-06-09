package com.ecommerce.admin.controller;

import com.ecommerce.admin.jwt.JwtAdminTokenProvider;
import com.ecommerce.library.dto.AdminDTO;
import com.ecommerce.library.entity.Admin;
import com.ecommerce.library.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
//    private JwtAdminTokenProvider jwtAdminTokenProvider;

    @Autowired
    private AdminServiceImpl adminService;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("adminDTO", AdminDTO.builder().build());
        return "register";
    }

    @GetMapping("/forgot_password")
    public String forgetForm(){
        return "forgot_password";
    }

    @GetMapping("/home")
    @PreAuthorize("hasRole('ADMIN')")
    public String homePage() {
        return "index";
    }

    @PostMapping("/register")
    public String addNewAdmin(
            @Valid @ModelAttribute("adminDTO") AdminDTO adminDTO,
            Model model,
            RedirectAttributes ra,
            BindingResult result
    ) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("adminDTO", adminDTO);
                ra.addFlashAttribute("message", result.toString());
                return "redirect:/api/register";
            }
            if (adminService.existsByUsername(adminDTO.getUsername())) {
                model.addAttribute("adminDTO", adminDTO);
                ra.addFlashAttribute("message", "Username existed!");
                return "redirect:/api/register";
            }
            if (adminService.existsByEmail(adminDTO.getEmail())) {
                model.addAttribute("adminDTO", adminDTO);
                ra.addFlashAttribute("message", "Email existed!");
                return "redirect:/api/register";
            }
            adminService.save(adminDTO);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "redirect:/api/register";
        }
        return "redirect:/api/login";
    }

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
//        String token = jwtAdminTokenProvider.generateToken((Admin) authentication.getPrincipal());

//        return ResponseEntity.ok().body(new AuthRes(token));
//    }
}