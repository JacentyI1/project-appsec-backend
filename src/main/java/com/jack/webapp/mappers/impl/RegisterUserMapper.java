package com.jack.webapp.mappers.impl;

import com.jack.webapp.domain.dto.RegisterUserDto;
import com.jack.webapp.domain.entities.UserEntity;
import com.jack.webapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserMapper implements Mapper<UserEntity, RegisterUserDto> {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;

    public RegisterUserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public RegisterUserDto mapTo(UserEntity userEntity) {
        return modelMapper.map(userEntity, RegisterUserDto.class);
    }

    @Override
    public UserEntity mapFrom(RegisterUserDto registerUserDto) {
        return modelMapper.map(registerUserDto, UserEntity.class);
    }
}
