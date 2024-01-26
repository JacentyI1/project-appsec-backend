package com.jack.webapp.controllers;

import com.jack.webapp.domain.requests.AuthRequestDto;
import com.jack.webapp.domain.requests.PasswordUpdateRequestDto;
import com.jack.webapp.domain.respones.AuthResponseDto;
import com.jack.webapp.domain.dto.LoginUserDto;
import com.jack.webapp.domain.requests.RegisterRequestDto;
import com.jack.webapp.domain.entities.UserEntity;
import com.jack.webapp.mappers.Mapper;
import com.jack.webapp.services.AuthenticationService;
import com.jack.webapp.services.JwtService;
import com.jack.webapp.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Log
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private  final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final Mapper<UserEntity, RegisterRequestDto> signupMapper;
    private final Mapper<UserEntity, LoginUserDto> loginMapper;


    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequestDto registerRequestDto) {
        return authenticationService.signup(signupMapper.mapFrom(registerRequestDto));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginUserDto request) throws Exception {
        AuthResponseDto authReposponse = authenticationService.authenticate(loginMapper.mapFrom(request));
        if(authReposponse == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(authReposponse, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
        if (response.getHeader(HttpHeaders.AUTHORIZATION) == null) {
            return new ResponseEntity<>("Failed to refresh token", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Token refreshed", HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody Long id, @RequestBody String postId) {
        if(userService.verifyUser(id, postId)) {
            return new ResponseEntity<>("Account verified seccessfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Verification failed", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/forgotten-password")
    public ResponseEntity<?> forgottenPassword(@RequestBody AuthRequestDto request) {
        boolean resetStatus = userService.resetPassword(request.getEmail());
        if(resetStatus) {
            return new ResponseEntity<>("A present with verification link has been sent to your email ;)", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Something went wrong. Try again later.", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/verify-temp-password")
    public ResponseEntity<?> verifyTempPassword(@RequestBody AuthRequestDto request) {
        boolean response =  authenticationService.verifyTempPassword(request.getEmail(), request.getPassword());
        if(response) {
            return ResponseEntity.ok("Password correct");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
    }

    @PatchMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordUpdateRequestDto request) {
        if(request.getNewPassword().isBlank()){
            return new ResponseEntity<>("Invalid input format", HttpStatus.BAD_REQUEST);
        }
        if(authenticationService.validChange(request.getEmailAddress(), request.getToken())){
            try{
                boolean updateStatus = userService.updatePassword(
                        request.getEmailAddress(),
                        request.getNewPassword(),
                        request.getConfirmPassword()
                );
                if (updateStatus) {
                    return ResponseEntity.ok("Password updated successfully.");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update password.");
                }
            } catch (RuntimeException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("Invalid input format", HttpStatus.BAD_REQUEST);
    }

}

