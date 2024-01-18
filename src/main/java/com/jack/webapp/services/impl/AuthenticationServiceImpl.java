package com.jack.webapp.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jack.webapp.controllers.authentication.AuthenticationResponse;
import com.jack.webapp.domain.dto.LoginUserDto;
import com.jack.webapp.domain.dto.RegisterUserDto;
import com.jack.webapp.domain.entities.Role;
import com.jack.webapp.domain.entities.UserEntity;
import com.jack.webapp.repositories.TokenRepository;
import com.jack.webapp.repositories.UserRepository;
import com.jack.webapp.services.AuthenticationService;
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
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final TokenRepository tokenRepository;


    @Override
    public UserEntity signup(RegisterUserDto input) {
        UserEntity user = UserEntity.builder()
                .fullName(input.getFullName())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .role(Role.USER)
                .build();
        return userRepository.save(user);
    }

    public UserEntity authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
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
                .annulled(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllCustomerTokens(UserEntity user) {
        var validTokens = tokenRepository.findAllValidTokenByCustomer(user.getId());
        if(validTokens.isEmpty()) return;
        validTokens.forEach(token -> {
            token.setAnnulled(true);
        });
        tokenRepository.saveAll(validTokens);
    }
}
