package com.jack.webapp.domain.dto;

import com.jack.webapp.domain.entities.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class RegisterUserDto {

//    private Long id;
    private String password;
    private String fullName;
    private String username;
    private String email;
    private Role role= Role.USER;
//    private List<Token> tokens;
//    private String verificationCode;
//    private boolean isActive;
}
