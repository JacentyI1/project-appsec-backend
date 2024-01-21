package com.jack.webapp.services;

import com.jack.webapp.domain.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Autowired
    private Environment env;

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
        return buildToken(extraClaims, userDetails, getExpiration());
    }

//    public String generateRefreshToken(UserDetails userDetails) {
//        return buildToken(new HashMap<>(), userDetails, getRefresh());
//    }

    public Long expirationTime(){
        return getExpiration();
    }

    public String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, Long expiration) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
//    public String buildToken(Map<String,Object> extraClaims, UserDetails userDetails, long expiration) {
//        if(expiration < 0) {
//            return Jwts.builder()
//                    .setClaims(extraClaims)
//                    .setIssuedAt(new Date(System.currentTimeMillis()))
//                    .setSubject(userDetails.getUsername())
//                    .signWith(getSignInKey())
//                    .compact();
//        }
//        return Jwts.builder()
//                .setClaims(extraClaims)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + expiration))
//                .setSubject(userDetails.getUsername())
//                .signWith(getSignInKey())
//                .compact();
//    }


    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /*
    * Check if token is expired
    * */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /*
    * extract expiration date from token
    * */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /*
    * extract all claims from token
    * */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /*
    * get signing key from secret key
    * */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String getSecretKey(){
        return env.getProperty("application.security.jwt.secret-key");
    }
    public Long getExpiration(){
        return Long.valueOf(env.getProperty("application.security.jwt.expiration"));
    }
    public Long getRefresh(){
        return Long.valueOf(env.getProperty("application.security.jwt.refresh-token.expiration"));
    }

    public String generateRefreshToken(UserEntity user) {
        return buildToken(new HashMap<>(), user, getRefresh());
    }
}
