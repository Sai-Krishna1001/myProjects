package com.virtusa.ars.dto;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String userId;
    private String userName;
    private String email;
    private String password;
    private UserRole userRole;
}
