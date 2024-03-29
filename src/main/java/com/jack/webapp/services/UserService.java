package com.jack.webapp.services;

import com.jack.webapp.domain.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

//    UserEntity registerUser()

    UserEntity save(UserEntity userEntity);

    List<UserEntity> findAll();

    Optional<UserEntity> findOne(Long id);

    boolean isExists(Long id);

    UserEntity partialUpdate(Long id, UserEntity userEntity);

    void delete(Long id);

    boolean verifyUser(Long id, String postId);

    boolean updatePassword(String address, String newPass, String confirmPass);

    UserEntity findOne(String email);


    void delete(String email);

    boolean resetPassword(String email);
}
