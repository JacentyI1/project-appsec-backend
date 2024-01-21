package com.jack.webapp.controllers;

import com.jack.webapp.domain.dto.LoginUserDto;
import com.jack.webapp.domain.dto.UserAccountResponseDto;
import com.jack.webapp.domain.entities.UserEntity;
import com.jack.webapp.mappers.Mapper;
import com.jack.webapp.services.UserService;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Log
@Controller
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final Mapper<UserEntity, LoginUserDto> loggedInMapper;

    private final Mapper<UserEntity, UserAccountResponseDto> accountMapper;
    public UserController(UserService userService, Mapper<UserEntity, LoginUserDto> loggedInMapper, Mapper<UserEntity, UserAccountResponseDto> accountMapper) {
        this.userService = userService;
        this.loggedInMapper = loggedInMapper;
        this.accountMapper = accountMapper;
    }

    @GetMapping("/account")
    public String authenticatedUser(Model model){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserEntity user = (UserEntity) authentication.getPrincipal();
            UserAccountResponseDto loggedInUser = accountMapper.mapTo(user);
            model.addAttribute("user", loggedInUser);
            return "userAccount";
        } catch (Exception e) {
            log.info("Access denied. User not found.\n" + "Exception: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null).toString();
        }
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
//        return new ResponseEntity<>(currentUser, HttpStatus.OK);
    }

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
