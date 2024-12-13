package com.cshare.user.dto.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateUserDto {
    private String id;
    private String username;
    private String email;
    private String name;
    private String avatarUrl;
    private String coverUrl;
}
