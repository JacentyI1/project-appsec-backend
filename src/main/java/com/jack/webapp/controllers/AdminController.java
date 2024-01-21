package com.jack.webapp.controllers;

import com.jack.webapp.domain.dto.LoginUserDto;
import com.jack.webapp.domain.dto.UserAccountResponseDto;
import com.jack.webapp.domain.entities.UserEntity;
import com.jack.webapp.mappers.Mapper;
import com.jack.webapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private Mapper<UserEntity, UserAccountResponseDto> userAccount;

    @GetMapping("/users")
    public ResponseEntity<List<UserAccountResponseDto>> getAllUsers() {
        List<UserEntity> users = userService.findAll();
        return new ResponseEntity<>(
                users.stream()
                .map(userAccount::mapTo)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<UserAccountResponseDto> getUser(@PathVariable("id") Long id) {
        Optional<UserEntity> foundUser = userService.findOne(id);
        return foundUser.map(userEntity -> {
            UserAccountResponseDto userDto = userAccount.mapTo(userEntity);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

        @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return new ResponseEntity<>("User deleted. :D",HttpStatus.NO_CONTENT);
    }


}
