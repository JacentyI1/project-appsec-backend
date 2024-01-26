package com.jack.webapp.domain.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {
    private String email;
    private String password;
}
