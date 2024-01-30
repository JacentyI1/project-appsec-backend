package com.jack.webapp.controllers;

import com.jack.webapp.domain.dto.LoginUserDto;
import com.jack.webapp.domain.dto.UserAccountResponseDto;
import com.jack.webapp.domain.entities.UserEntity;
import com.jack.webapp.exceptionHandlers.ResourceNotFoundException;
import com.jack.webapp.mappers.Mapper;
import com.jack.webapp.services.JwtService;
import com.jack.webapp.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Log
@RestController
@RequestMapping("/api/users")
@CrossOrigin(originPatterns = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
public class UserController {
    private final UserService userService;
    private final Mapper<UserEntity, LoginUserDto> loggedInMapper;

    private final Mapper<UserEntity, UserAccountResponseDto> accountMapper;
    @Autowired
    private final JwtService jwtService;


    public UserController(UserService userService, Mapper<UserEntity, LoginUserDto> loggedInMapper, Mapper<UserEntity, UserAccountResponseDto> accountMapper, JwtService jwtService) {
        this.userService = userService;
        this.loggedInMapper = loggedInMapper;
        this.accountMapper = accountMapper;
        this.jwtService = jwtService;
    }

    @GetMapping("/account")
    public ResponseEntity<UserAccountResponseDto> authenticatedUser(Principal principal) {
        try {
            UserEntity userEntity = userService.findOne(principal.getName());
            log.info("userDto: " + userEntity.getEmail());
            log.info("userDto: " + userEntity.getGamer());
            return new ResponseEntity<>(accountMapper.mapTo(userEntity), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            log.info( "Exception: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

    @DeleteMapping(path = "/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable("email") String email) {
        userService.delete(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//
//


}
