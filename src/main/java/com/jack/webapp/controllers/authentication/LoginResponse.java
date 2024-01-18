package com.jack.webapp.controllers.authentication;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponse {

    private String token;

    private Long expiresIn;
}
