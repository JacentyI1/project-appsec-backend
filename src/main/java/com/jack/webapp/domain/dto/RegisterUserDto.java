package com.jack.webapp.domain.dto;

import com.jack.webapp.domain.entities.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class RegisterUserDto {

    private String password;
    private String fullName;
    private String email;
    private Role role;
}
