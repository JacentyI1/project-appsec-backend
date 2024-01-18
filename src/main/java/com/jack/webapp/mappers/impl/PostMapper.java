//package com.jack.webapp.mappers.impl;
//
//import com.jack.webapp.domain.dto.PostDto;
//import com.jack.webapp.domain.entities.PostEntity;
//import com.jack.webapp.mappers.Mapper;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Component;
//
//@Component
//public class PostMapper implements Mapper<PostEntity, PostDto> {
//    private ModelMapper modelMapper;
//
//    public PostMapper(ModelMapper modelMapper) {
//        this.modelMapper = modelMapper;
//    }
//
//
//    @Override
//    public PostDto mapTo(PostEntity postEntity) {
//        return modelMapper.map(postEntity, PostDto.class);
//    }
//
//    @Override
//    public PostEntity mapFrom(PostDto postDto) {
//        return modelMapper.map(postDto, PostEntity.class);
//    }
//}
