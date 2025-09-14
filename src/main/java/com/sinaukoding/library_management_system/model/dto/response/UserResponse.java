package com.sinaukoding.library_management_system.model.dto.response;

import com.sinaukoding.library_management_system.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String username;
    private String namaLengkap;
    private String email;
    private Role role;
    private LocalDateTime expiredTokenAt;
}
