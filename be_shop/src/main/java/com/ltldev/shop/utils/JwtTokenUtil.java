package com.ltldev.shop.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class JwtTokenUtil {

    @Value("${jwt.expiration}")
    private Long expiration; // time login, save to environment variable

    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(com.ltldev.shop.models.User user) {
        // properties => claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("phoneNumber", user.getPhoneNumber());
        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(getSignInkey(), SignatureAlgorithm.ES256)
                    .compact();
            return token;
        } catch (Exception e) {
            System.out.printf("Cannot create jwt token, error: "+e.getMessage());
            return null;
        }
    }




    public Claims extractAllClaims(String token){
        return null;
    }

    // Mã hóa một kỹ tự rồi sẽ công vào với số đt mã hóa => key token
    private Key getSignInkey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }
}
