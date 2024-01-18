package com.jack.webapp.services;

import com.jack.webapp.domain.dto.LoginUserDto;
import com.jack.webapp.domain.dto.RegisterUserDto;
import com.jack.webapp.domain.entities.UserEntity;

public interface AuthenticationService {


    UserEntity signup(RegisterUserDto registerUserDto);

    UserEntity authenticate(LoginUserDto loginUserDto);
}
