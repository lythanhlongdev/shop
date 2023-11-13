package com.ltldev.shop.components;


import com.ltldev.shop.exception.InvalidParamException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@RequiredArgsConstructor
@Component
public class JwtTokenUtil {

    @Value("${jwt.expiration}")
    private Long expiration; // time login, save to environment variable

    // chữ ký điện tử => JTW(HashCode, Object, Chữ ký tiện tử)
    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(com.ltldev.shop.models.User user) throws Exception{
        //properties => claims
        Map<String, Object> claims = new HashMap<>();
        //this.generateSecretKey();
        claims.put("phoneNumber", user.getPhoneNumber());
        try {
            String token = Jwts.builder()
                    .setClaims(claims) //how to extract claims from this ?
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();

            return token;
        }catch (Exception e) {
            //you can "inject" Logger, instead System.out.println
            throw new InvalidParamException("Cannot create jwt token, error: "+e.getMessage());
            //return null;
        }
    }

    // mã hpa chữ ký điện tử
    private Key getSignInKey() {
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        //Keys.hmacShaKeyFor(Decoders.BASE64.decode("TaqlmGv1iEDMRiFp/pHuID1+T84IABfuA0xXh4GhiUI="));
        return Keys.hmacShaKeyFor(bytes);
    }

    // tạo mã điện tử tự động
    private String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32]; // 256-bit key
        random.nextBytes(keyBytes);
        String secretKey = Encoders.BASE64.encode(keyBytes);
        return secretKey;
    }

    // kiếm tra mã đã
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    //check expiration
    public boolean isTokenExpired(String token) {
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }
    public String extractPhoneNumber(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        String phoneNumber = extractPhoneNumber(token);
        return (phoneNumber.equals(userDetails.getUsername()))
                && !isTokenExpired(token);
    }

//    public String generateToken(com.ltldev.shop.models.User user) throws Exception {
//        // properties => claims
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("phoneNumber", user.getPhoneNumber());
////        this.generateSecretKey();
//        try {
//            String token = Jwts.builder()
//                    .setClaims(claims)
//                    .setSubject(user.getPhoneNumber())
//                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
//                    .signWith(getSignInkey(), SignatureAlgorithm.ES256)
//                    .compact();
//            return token;
//        } catch (Exception e) {
////            System.out.printf("Cannot create jwt token, error: " + e.getMessage());
//            throw new InvalidParamException("Cannot create jwt token, error");
//        }
//    }
//
//
//    // get all claims
//    private Claims extractAllClaims(String token) {
//
//        return Jwts
//                .parserBuilder()
//                .build()
//                .parseClaimsJwt(token)
//                .getBody();
//    }
//
//    private String generateSecretKey() {
//        SecureRandom secureRandom = new SecureRandom();
//        byte[] bytesKey = new byte[32];// 256-bit key
//        secureRandom.nextBytes(bytesKey);
//        String secretKey = Encoders.BASE64.encode(bytesKey);
//        return secretKey;
//
//    }// oaT1pTENPcS4daPtCWn7+D/6PR7p0e4lltBNLnVVKTg=
//
//    // get one claims
//    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
//        final Claims claims = this.extractAllClaims(token);
//        return claimsTFunction.apply(claims);
//    }
//
//    // check time
//    public boolean isTokenExpired(String token) {
//        final Date expirationeDate = this.extractClaims(token, Claims::getExpiration);
//        return expirationeDate.before(new Date());
//
//    }
//
//    // Mã hóa một kỹ tự rồi sẽ công vào với số đt mã hóa => key token
//    private Key getSignInkey() {
//        byte[] bytes = Decoders.BASE64.decode(secretKey);
//        return Keys.hmacShaKeyFor(bytes);
//    }
}
