package com.jack.webapp.services.impl;

import com.jack.webapp.domain.entities.UserEntity;
import com.jack.webapp.repositories.UserRepository;
import com.jack.webapp.services.JwtService;
import com.jack.webapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService senderService;

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
    public boolean verifyUser(Long id, Long postId) {
        return true;
    }

    @Override
    public boolean resetPassword(String emailAddress) {
//        try {
//            Optional<UserEntity> customerOptional = userRepository.findByEmail(emailAddress);
//            if (customerOptional.isPresent()) {
//                UserEntity user = customerOptional.get();
//
//                String tempPassword = generateTempPassword();
//                String hashedTempPassword = passwordEncoder.encode(tempPassword);
//                saveTemporaryPassword(hashedTempPassword, user);
//                userRepository.save(user);
//                sendResetPasswordEmail(emailAddress, tempPassword);
//                return true;
//            } else {
//                System.out.println("Customer not found");
//                return false;
//            }
//        } catch (IncorrectResultSizeDataAccessException ex) {
//            System.out.println("Error: Multiple accounts found with the same email address.");
//            return false;
//        } catch (Exception ex) {
//            System.out.println("An unexpected error occurred: " + ex.getMessage());
//            return false;
//        }
        return false;
    }

    private void sendResetPasswordEmail(String emailAddress, JwtService token) {
        String subject = "Reset your Password";
        String message = "Your temporary password is:"+token;
        senderService.sendMail(emailAddress, subject, message);
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


}
