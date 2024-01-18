package com.jack.webapp.mappers.impl;

import com.jack.webapp.domain.dto.RegisterUserDto;
import com.jack.webapp.domain.entities.UserEntity;
import com.jack.webapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<UserEntity, RegisterUserDto> {

    private ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
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
