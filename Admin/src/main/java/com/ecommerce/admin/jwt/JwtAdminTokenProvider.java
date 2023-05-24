package com.ecommerce.admin.jwt;

import com.ecommerce.library.entity.Admin;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtAdminTokenProvider {
    private static final long EXPIRE_DURATION = 24 * 60 * 60 * 1000; // 24 hour

    private static final String SECRET_KEY = "ADMIN_Khanhtrang1307!";

    public String generateToken(Admin admin){
        return Jwts.builder()
                .setSubject(admin.getUsername())
                .claim("adminId", admin.getId())
                .claim("authorities", admin.getAuthorities())   //extra content to put into the payload
                .setIssuer("shoppers.com")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    //Get username
    public String getSubject(String token){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
