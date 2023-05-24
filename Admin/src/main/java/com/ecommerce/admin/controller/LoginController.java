package com.ecommerce.admin.controller;

import com.ecommerce.admin.jwt.JwtAdminTokenProvider;
import com.ecommerce.library.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/api")
public class LoginController {
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtAdminTokenProvider jwtAdminTokenProvider;

    @GetMapping("/login")
    public String getLoginView() {
        return "login";
    }

    @GetMapping("/home")
    public String getSuccessView() {
        return "index";
    }


    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@ModelAttribute @Valid AuthReq authReq) throws BadCredentialsException {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authReq.getUsername(),
                        authReq.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtAdminTokenProvider.generateToken((Admin) authentication.getPrincipal());

        return ResponseEntity.ok().body(new AuthRes(token));
    }
}