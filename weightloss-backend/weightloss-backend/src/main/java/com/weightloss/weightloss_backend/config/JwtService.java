package com.weightloss.weightloss_backend.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
	
	private static final String SECRET_KEY = "u7R6d2wK8F1k3M9nP4t2s8V0q5yX1z3L6m7a2b4c8e5r9t1y0u6i2o8p5w9x3"; // at least 64 chars

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);  
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24h
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

//    @Value("${jwt.secret}")
//    private String jwtSecret;
//
//    @Value("${jwt.expiration-ms}")
//    private long jwtExpirationMs;
//
//    private Key signingKey;
//
//    @PostConstruct
//    public void init() {
//        signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
//    }
//
//    public String generateToken(String subject, String role) {
//        Date now = new Date();
//        Date exp = new Date(now.getTime() + jwtExpirationMs);
//
//        return Jwts.builder()
//                .setSubject(subject) // usually email or username
//                .claim("role", role)
//                .setIssuedAt(now)
//                .setExpiration(exp)
//                .signWith(signingKey, SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token);
//            return true;
//        } catch (JwtException | IllegalArgumentException ex) {
//            // log if needed
//            return false;
//        }
//    }
//
//    public String extractUsername(String token) {
//        Claims claims = Jwts.parserBuilder().setSigningKey(signingKey).build()
//                .parseClaimsJws(token).getBody();
//        return claims.getSubject();
//    }
//    
//    public <T> T extractClaim(String token, String claimKey, Class<T> clazz) {
//        Claims claims = Jwts.parserBuilder().setSigningKey(signingKey).build()
//                          .parseClaimsJws(token).getBody();
//        return claims.get(claimKey, clazz);
//    }
}
