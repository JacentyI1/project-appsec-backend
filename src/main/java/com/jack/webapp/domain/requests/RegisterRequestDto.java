package com.jack.webapp.domain.requests;

import com.jack.webapp.domain.entities.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterRequestDto {

    private String username;
    private String fullName;
    private String email;
    private String password;
    private Role role= Role.USER;
}
