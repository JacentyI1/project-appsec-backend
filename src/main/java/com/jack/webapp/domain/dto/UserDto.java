package com.jack.webapp.domain.dto;

import com.jack.webapp.domain.entities.Role;
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

    private String email;

    private String username;

    private String password;

    private Role role;

}
