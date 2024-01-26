package com.jack.webapp.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordUpdateRequestDto {
    private String emailAddress;
    private String newPassword;
    private String confirmPassword;
    private String token;
}
