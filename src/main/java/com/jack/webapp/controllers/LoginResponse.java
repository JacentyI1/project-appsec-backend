package com.jack.webapp.controllers;

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
