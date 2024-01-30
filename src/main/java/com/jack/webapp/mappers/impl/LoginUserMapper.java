package com.jack.webapp.mappers.impl;

import com.jack.webapp.domain.dto.LoginUserDto;
import com.jack.webapp.domain.entities.UserEntity;
import com.jack.webapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LoginUserMapper implements Mapper<UserEntity, LoginUserDto> {
    private ModelMapper modelMapper;

    public LoginUserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public LoginUserDto mapTo(UserEntity user) {
        return modelMapper.map(user, LoginUserDto.class);
    }

    @Override
    public UserEntity mapFrom(LoginUserDto loginUserDto) {
        return modelMapper.map(loginUserDto, UserEntity.class);
    }


}
