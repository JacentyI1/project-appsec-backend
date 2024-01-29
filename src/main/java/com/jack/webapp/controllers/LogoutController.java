//package com.jack.webapp.controllers;
//
//import com.jack.webapp.repositories.TokenRepository;
//import com.jack.webapp.services.JwtService;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/logout")
//public class LogoutController {
//    @Autowired
//    final TokenRepository tokenRepository;
//    @Autowired
//    final JwtService jwtService;
//
//    public LogoutController(TokenRepository tokenRepository, JwtService jwtService) {
//        this.tokenRepository = tokenRepository;
//        this.jwtService = jwtService;
//    }
//
//    @PostMapping("/")
//    public ResponseEntity<?> logout(HttpServletRequest request, @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
//
//        return null;
//    }
//
//}
