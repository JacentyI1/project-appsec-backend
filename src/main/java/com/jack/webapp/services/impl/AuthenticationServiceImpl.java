package com.jack.webapp.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jack.webapp.controllers.authentication.AuthenticationRequest;
import com.jack.webapp.controllers.authentication.AuthenticationResponse;
import com.jack.webapp.domain.entities.UserEntity;
import com.jack.webapp.repositories.TokenRepository;
import com.jack.webapp.repositories.UserRepository;
import com.jack.webapp.services.AuthenticationService;
import com.jack.webapp.services.EmailSenderService;
import com.jack.webapp.services.JwtService;
import com.jack.webapp.services.UserService;
import com.jack.webapp.token.Token;
import com.jack.webapp.token.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final EmailSenderService emailSenderService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public ResponseEntity<String> signup(UserEntity user) {
        Optional<UserEntity> existingUser = userRepository.findByEmail(user.getEmail()); // check if user exists
        if(existingUser.isPresent()) return new ResponseEntity<String>("An account with this email already exists.", HttpStatus.CONFLICT); // return conflict if user exists

        user.setPassword(passwordEncoder.encode(user.getPassword())); // encode password
        user.setCreatedAt(new Date()); // set created at

        String verificationCode = jwtService.buildToken(new HashMap<>(), user, -1L); // build token
        user.setVerificationCode(verificationCode); // set verification code

        user.setActive(false); // set active to false

        userRepository.save(user); // save user
        sendVerificationMail(user.getEmail(), user.getVerificationCode(), user.getId());
        return new ResponseEntity<String>("Account created. Check e-mail.", HttpStatus.CREATED);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmailAddress(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmailAddress()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllCustomerTokens(user);
        saveCustomerToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(jwtToken)
                .build();

    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String email;
        if(authHeader == null || !authHeader.startsWith("Bearer ")) return;
        refreshToken = authHeader.substring(7);
        email = jwtService.extractUsername(refreshToken);
        if(email != null) {
            var customer = this.userRepository.findByEmail(email).orElseThrow();
            if(jwtService.isTokenValid(refreshToken, customer)) {
                var accessToken = jwtService.generateToken(customer);
                revokeAllCustomerTokens(customer);
                saveCustomerToken(customer, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public void sendVerificationMail(String email, String pass, Long id){
        emailSenderService.sendEmail(email, "Shitpost Generator SignUp", "Your registration link is: " + pass + "\n" +
                "Please verify your account by clicking the link below:\n" +
                "http://localhost:8080/api/auth/verify?" + pass + "&id=" + id);
    }

// Placeholder
    @Override
    public boolean verifyUser(Long id, Long postId) {
        return userService.verifyUser(id, postId);
    }

    private void saveCustomerToken(UserEntity user, String accessToken) {
        var token = Token.builder()
                .user(user)
                .token(accessToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllCustomerTokens(UserEntity user) {
        var validTokens = tokenRepository.findAllValidTokenByCustomer(user.getId());
        if(validTokens.isEmpty()) return;
        validTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenRepository.saveAll(validTokens);
    }
}
