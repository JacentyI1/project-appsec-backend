package com.jack.webapp.services;

import com.jack.webapp.controllers.authentication.AuthenticationResponse;
import com.jack.webapp.domain.dto.LoginUserDto;
import com.jack.webapp.domain.entities.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface AuthenticationService {


    ResponseEntity<String> signup(UserEntity registerUserDto);

    AuthenticationResponse authenticate(LoginUserDto loginUser);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    boolean verifyUser(Long id, Long postId);
}
