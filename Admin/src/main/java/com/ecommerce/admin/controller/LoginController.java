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
import org.springframework.web.bind.annotation.*;
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
    public String getLoginView() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterView(Model model){
        model.addAttribute("adminDTO", AdminDTO.builder().build());
        return "register";
    }

    @GetMapping("/home")
    @PreAuthorize("hasRole('ADMIN')")
    public String getSuccessView() {
        return "home";
    }

    @PostMapping("/register")
    public String addNewAdmin(@Valid @ModelAttribute("adminDTO") AdminDTO adminDTO){
        adminService.save(adminDTO);
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