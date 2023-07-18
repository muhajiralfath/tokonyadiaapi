package com.enigma.tokonyadia.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtils {

    @Value("${tokonyadia.jwt-secret}")
    private String jwtSecret;

    @Value("${tokonyadia.jwt-expiration}")
    private Long jwtExpiration;

    public String getEmailByToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret)
                .parseClaimsJws(token).getBody().getSubject();
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .compact();
    }

    public boolean validateJwtToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e){
            log.error("Invalid JWT Token {}", e.getMessage());
        } catch (ExpiredJwtException e){
            log.error("JWT Token is Expired {}", e.getMessage());
        } catch (UnsupportedJwtException e){
            log.error("JWT Token is Unsupported {}", e.getMessage());
        } catch (IllegalArgumentException e){
            log.error("JWT claims string is empety {}", e.getMessage());
        }
        return false;
    }
}
