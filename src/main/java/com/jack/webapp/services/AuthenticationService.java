package com.jack.webapp.services;

import com.jack.webapp.domain.dto.LoginUserDto;
import com.jack.webapp.domain.dto.RegisterUserDto;
import com.jack.webapp.domain.entities.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {


    UserEntity signup(RegisterUserDto registerUserDto);

    UserEntity authenticate(LoginUserDto loginUserDto);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

    boolean verifyUser(Long id, Long postId);
}
