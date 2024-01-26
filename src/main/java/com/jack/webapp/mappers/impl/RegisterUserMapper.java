package com.jack.webapp.mappers.impl;

import com.jack.webapp.domain.requests.RegisterRequestDto;
import com.jack.webapp.domain.entities.UserEntity;
import com.jack.webapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserMapper implements Mapper<UserEntity, RegisterRequestDto> {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;

    public RegisterUserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public RegisterRequestDto mapTo(UserEntity userEntity) {
        return modelMapper.map(userEntity, RegisterRequestDto.class);
    }

    @Override
    public UserEntity mapFrom(RegisterRequestDto registerRequestDto) {
        return modelMapper.map(registerRequestDto, UserEntity.class);
    }
}
