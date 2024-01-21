package com.jack.webapp.domain.dto;


import com.jack.webapp.domain.entities.Role;
import lombok.*;

import java.util.Date;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountResponseDto {
    private String username;
    private String fullName;
    private String email;
    private Date createdAt;
    private Role role;
    private boolean isActive;
}
