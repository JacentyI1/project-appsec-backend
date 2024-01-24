package com.jack.webapp.controllers;

import com.jack.webapp.controllers.authentication.AuthenticationResponse;
import com.jack.webapp.domain.dto.LoginUserDto;
import com.jack.webapp.domain.dto.UserAccountResponseDto;
import com.jack.webapp.domain.entities.UserEntity;
import com.jack.webapp.mappers.Mapper;
import com.jack.webapp.services.JwtService;
import com.jack.webapp.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Log
@RestController
@RequestMapping("/users")
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
    public ResponseEntity<UserAccountResponseDto> authenticatedUser(HttpServletRequest request) {
        // Get the user's information from the session
        AuthenticationResponse authResponse = (AuthenticationResponse) request.getSession().getAttribute("user");
        if(authResponse != null) {
            String email = jwtService.extractUsername(authResponse.getAccessToken());
            UserEntity user = userService.findOne(email);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            UserAccountResponseDto loggedInUser = accountMapper.mapTo(user);
            return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }

//    @GetMapping("/account")
//    public ResponseEntity<UserAccountResponseDto> authenticatedUser(HttpServletRequest request) {
//        try {
//            String header = request.getHeader("Authorization");
//            log.info( "header: " + header);
//            if(header != null && header.startsWith("Bearer ")) {
//                log.info( "Got through the header check");
//                final String jwt = header.substring(7);
//                final String email = jwtService.extractUsername(jwt); // assuming jwtService is autowired in your controller
//                UserEntity user = userService.findOne(email);
//                if (user == null) {
//                    throw new UsernameNotFoundException("User not found");
//                }
//                UserAccountResponseDto loggedInUser = accountMapper.mapTo(user);
//                return new ResponseEntity<>(loggedInUser, HttpStatus.OK);
//            } else {
//                log.info( "Invalid Authorization header");
//                throw new IllegalArgumentException("Invalid Authorization header");
//            }
//        } catch (Exception e) {
//            log.info( "Exception: " + e.getMessage());
//            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
//        }
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
////        return new ResponseEntity<>(currentUser, HttpStatus.OK);
//    }

//    @GetMapping("/")
//    public ResponseEntity<List<UserEntity>> allUsers() {
//        List<UserEntity> users = userService.findAll();
//        return new ResponseEntity<>(users, HttpStatus.OK);
//    }

    //    private UserService userService;
//
//    private Mapper<UserEntity, UserDto> userMapper;
//
//    public UserController(UserService userService, Mapper<UserEntity, UserDto> userMapper) {
//        this.userService = userService;
//        this.userMapper = userMapper;
//    }
//
//    @PostMapping(path = "/v1/users")
//    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
//        UserEntity userEntity = userMapper.mapFrom(user);
//        UserEntity savedUserEntity = userService.save(userEntity);
//        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.CREATED);
//    }
//
//    @PostMapping(path = "/v1/register")
//    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto user) {
//        UserEntity userEntity = userMapper.mapFrom(user);
//        UserEntity savedUserEntity = userService.save(userEntity);
//        return new ResponseEntity<>(userMapper.mapTo(savedUserEntity), HttpStatus.CREATED);
//    }
//
//    @GetMapping(path = "/v1/users")
//    public List<UserDto> listUsers() {
//        List<UserEntity> users = userService.findAll();
//        return users.stream().map(userMapper::mapTo).collect(Collectors.toList());
//    }
//
//    @GetMapping(path = "/v1/users/{id}")
//    public ResponseEntity<UserDto> getUser(@PathVariable("id") Long id) {
//        Optional<UserEntity> foundUser = userService.findOne(id);
//        return foundUser.map(userEntity -> {
//            UserDto userDto = userMapper.mapTo(userEntity);
//            return new ResponseEntity<>(userDto, HttpStatus.OK);
//        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @PutMapping(path = "/v1/users/{id}")
//    public ResponseEntity<UserDto> fullUpdateUser(@PathVariable("id") Long id,
//                                                  @RequestBody UserDto userDto) {
//        if(!userService.isExists(id)) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        userDto.setId(id);
//        UserEntity userEntity = userMapper.mapFrom(userDto);
//        UserEntity savedUserEntity = userService.save(userEntity);
//        return new ResponseEntity<>(
//                userMapper.mapTo(savedUserEntity),
//                HttpStatus.OK
//        );
//
//    }
//
//    @PatchMapping(path = "/v1/user/{id}")
//    public ResponseEntity<UserDto> partialUpdate(
//            @PathVariable("id") Long id,
//            @RequestBody UserDto  userDto
//    ) {
//        if(!userService.isExists(id)) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        UserEntity userEntity = userMapper.mapFrom(userDto);
//        UserEntity updatedUser = userService.partialUpdate(id, userEntity);
//        return new ResponseEntity<>(userMapper.mapTo(updatedUser), HttpStatus.OK);
//    }
//
//    @DeleteMapping(path = "/v1/users/{id}")
//    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
//        userService.delete(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//


}
