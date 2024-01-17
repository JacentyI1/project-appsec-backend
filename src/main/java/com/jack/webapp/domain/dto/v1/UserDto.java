package com.jack.webapp.domain.dto.v1;

import com.jack.webapp.domain.entities.v1.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "password is required")
    private String password;
    @NotBlank(message = "Name is required")
    private String email;

    private Role role;

}
