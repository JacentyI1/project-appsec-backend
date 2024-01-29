package com.jack.webapp.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jack.webapp.domain.entities.TempPassword;
import com.jack.webapp.domain.respones.AuthResponseDto;
import com.jack.webapp.domain.entities.UserEntity;
import com.jack.webapp.repositories.TempPasswordRepository;
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
    private TempPasswordRepository tempRepository;


    @Override
    public ResponseEntity<String> signup(UserEntity user) {

        Optional<UserEntity> existingUser = userRepository.findByEmail(user.getEmail());
        if(existingUser.isPresent()) return new ResponseEntity<String>("An account with this email already exists.", HttpStatus.CONFLICT);

//        user.setFullName(user.getFullName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(new Date());
        String verificationToken = jwtService.buildToken(new HashMap<>(), user, -1L);
        user.setVerificationCode(verificationToken);
        user.setActive(false);
        userRepository.save(user);

    // uncomment when email service works xd
        sendVerificationMail(user.getEmail(), user.getVerificationCode(), user.getId());
        return new ResponseEntity<String>("Account created. Check e-mail.", HttpStatus.CREATED);
    }

    public AuthResponseDto authenticate(UserEntity request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        UserEntity user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
//        AuthenticationC
        return AuthResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }


    public void sendVerificationMail(String email, String pass, Long id){
        emailSenderService.sendEmail(email, "Random Post Generator SignUp",
                "Your registration link is: " + pass + "\n" +
                "Please verify your account by clicking the link below:\n" +
                "http://localhost:8080/api/auth/verify?" +
                        "code=" + pass + "&id=" + id);
    }

    @Override
    public boolean verifyTempPassword(String email, String password) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            TempPassword passwd = tempRepository.findValidTokenByUser(user.getId());
            if (passwd != null && passwordEncoder.matches(password, passwd.token)) {
                passwd.setRevoked(true);
                tempRepository.save(passwd);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean validChange(String emailAddress, String token) {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(emailAddress);
        if (userEntityOptional.isPresent()) {
            UserEntity user = userEntityOptional.get();
            TempPassword passwd = tempRepository.findAgainValid(user.getId());
            passwd.used = true;
            tempRepository.save(passwd);
            return passwordEncoder.matches(token, passwd.token);
        }
        return false;
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
            var user = this.userRepository.findByEmail(email).orElseThrow();
            if(jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthResponseDto.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private void saveUserToken(UserEntity user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(UserEntity user) {
        var validTokens = tokenRepository.findAllValidTokenByCustomer(user.getId());
        if(validTokens.isEmpty()) return;
        validTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });
        tokenRepository.saveAll(validTokens);
    }
}
