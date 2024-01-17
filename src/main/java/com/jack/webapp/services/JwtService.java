package com.jack.webapp.services;

import lombok.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

//    public String extractUsernae(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }

//    private <T> T extractClaim(String token, Object o) {
//        return ;
//    }
}
