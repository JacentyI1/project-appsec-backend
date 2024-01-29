package com.jack.webapp.services.impl;

import com.jack.webapp.domain.entities.TempPassword;
import com.jack.webapp.domain.entities.UserEntity;
import com.jack.webapp.repositories.TempPasswordRepository;
import com.jack.webapp.repositories.UserRepository;
import com.jack.webapp.services.JwtService;
import com.jack.webapp.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Log
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderServiceImpl emailSenderServiceImpl;
    private final TempPasswordRepository tempRepository;

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public List<UserEntity> findAll() {
        return new ArrayList<>(userRepository.findAll());
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
//            Optional.ofNullable(userEntity.getRoleEntities()).ifPresent(existingUser::setRoleEntities);
            Optional.ofNullable(userEntity.getUsername()).ifPresent(existingUser::setEmail);
            Optional.ofNullable(userEntity.getPassword()).ifPresent(existingUser::setPassword);
            return userRepository.save(existingUser);
        }).orElseThrow(()-> new RuntimeException("Author does not exist"));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean verifyUser(Long id, String code) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()){
            UserEntity user = userOptional.get();
            if(user.getVerificationCode().equals(code)){
                user.setActive(true);
                userRepository.save(user);
                return true;
            }
        }
        return false;
    }

    private void sendResetPasswordEmail(String emailAddress, String token) {
        String subject = "Reset your Password";
        String message = "Your temporary password is:"+token;
        emailSenderServiceImpl.sendEmail(emailAddress, subject, message);
    }

    @Override
    public boolean updatePassword(String address, String newPass, String confirmPass) {
        if(!newPass.equals(confirmPass)){
            throw new RuntimeException("Passwords didn't match.");
        }
        Optional<UserEntity> userOptional = userRepository.findByEmail(address);
        if(userOptional.isPresent()){
            UserEntity user = userOptional.get();
            String hashedPassword = passwordEncoder.encode(newPass);
            user.setPassword(hashedPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public UserEntity findOne(String name) {
        UserEntity user = userRepository.findByEmail(name).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(null);
        return user;
    }


    @Override
    public void delete(String email) {
        UserEntity toBeDeletedUser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(toBeDeletedUser);
    }

    @Override
    public boolean resetPassword(String email) {
        try {
            Optional<UserEntity> customerOptional = userRepository.findByEmail(email);
            if (customerOptional.isPresent()) {
                UserEntity userEntity = customerOptional.get();

                String tempPassword = generateTempPassword();
                String hashedTempPassword = passwordEncoder.encode(tempPassword);
                saveTemporaryPassword(hashedTempPassword, userEntity);
                userRepository.save(userEntity);
                sendResetPasswordEmail(email, tempPassword);
                return true;
            } else {
                log.info("Customer not found");
                return false;
            }
        } catch (IncorrectResultSizeDataAccessException ex) {
            log.info("Error: Multiple accounts found with the same email address.");
            return false;
        } catch (Exception ex) {
            log.info("An unexpected error occurred: " + ex.getMessage());
            return false;
        }
    }

    private void saveTemporaryPassword(String hashedTempPassword, UserEntity userEntity) {
        TempPassword tempPassword = new TempPassword();
        tempPassword.setToken(hashedTempPassword);
        tempPassword.setUser(userEntity);
        tempPassword.setRevoked(false);
        tempPassword.setExpired(false);
        tempRepository.save(tempPassword);
    }

    private String generateTempPassword() {
        String characters = "ABCDEFGHJKLMNOPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz0123456789!@#$%^&*()";
        Random random = new SecureRandom();
        StringBuilder tempPassword = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            tempPassword.append(characters.charAt(index));
        }

        return tempPassword.toString();
    }

}
