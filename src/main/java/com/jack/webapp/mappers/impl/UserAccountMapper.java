package com.jack.webapp.mappers.impl;

import com.jack.webapp.domain.dto.UserAccountResponseDto;
import com.jack.webapp.domain.entities.UserEntity;
import com.jack.webapp.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAccountMapper implements Mapper<UserEntity, UserAccountResponseDto> {
    @Autowired
    private ModelMapper modelMapper;

    public UserAccountMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserAccountResponseDto mapTo(UserEntity user) {
        UserAccountResponseDto accountResponseDto = this.modelMapper.map(user, UserAccountResponseDto.class);
        accountResponseDto.setUsername(user.getGamer());
        return accountResponseDto;
    }

    @Override
    public UserEntity mapFrom(UserAccountResponseDto userAccountResponseDto) {
        return this.modelMapper.map(userAccountResponseDto, UserEntity.class);
    }
}
