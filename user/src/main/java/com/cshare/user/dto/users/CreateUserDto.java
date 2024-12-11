package com.cshare.user.dto.users;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserDto {
    private String username;
    private String email;
    private String name;
    private String avatarUrl;
    private String coverUrl;
}
