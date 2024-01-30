package com.jack.webapp.services;

import com.jack.webapp.domain.respones.AuthResponseDto;
import com.jack.webapp.domain.entities.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AuthenticationService {


    ResponseEntity<String> signup(UserEntity registerUserDto);

    AuthResponseDto authenticate(UserEntity loginUser);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;


    boolean verifyTempPassword(String email, String password);

    boolean validChange(String emailAddress, String token);

}
