package com.jack.webapp.services.impl;

import com.jack.webapp.domain.entities.UserEntity;
import com.jack.webapp.repositories.UserRepository;
import com.jack.webapp.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public List<UserEntity> findAll() {
        return StreamSupport.stream(
                userRepository.findAll().spliterator(),
                        false
                ).collect(Collectors.toList()
        );
    }

    @Override
    public Optional<UserEntity> findOne(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public UserEntity partialUpdate(Long id, UserEntity userEntity) {
        userEntity.setId(id);
        return userRepository.findById(id).map(existingUser -> {
            Optional.ofNullable(userEntity.getRole()).ifPresent(existingUser::setRole);
            Optional.ofNullable(userEntity.getUsername()).ifPresent(existingUser::setUsername);
            Optional.ofNullable(userEntity.getPassword()).ifPresent(existingUser::setPassword);
            return userRepository.save(existingUser);
        }).orElseThrow(()-> new RuntimeException("Author does not exist"));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }


}
