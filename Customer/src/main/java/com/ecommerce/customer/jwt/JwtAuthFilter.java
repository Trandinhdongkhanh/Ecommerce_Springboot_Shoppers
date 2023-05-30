package com.ecommerce.customer.jwt;

import com.ecommerce.customer.config.CustomerDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JwtCustomerTokenProvider jwtCustomerTokenProvider;
    @Autowired
    private CustomerDetailsService customerDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getJwtTokenFromReq(request);
        if (token != null && jwtCustomerTokenProvider.validateToken(token)) {
            setAuthenticationContext(token, request);
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthenticationContext(String token, HttpServletRequest request) {
        String username = jwtCustomerTokenProvider.getSubject(token);
        UserDetails userDetails = customerDetailsService.loadUserByUsername(username);

        if (userDetails != null) {

            UsernamePasswordAuthenticationToken
                    authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private String getJwtTokenFromReq(HttpServletRequest req) {
        Optional<String> header = Optional.ofNullable(req.getHeader("Authorization"));
        if (header.isPresent() && header.get().startsWith("Bearer ")) {
            return header.get().substring(7);
        }
        return null;
    }
}
