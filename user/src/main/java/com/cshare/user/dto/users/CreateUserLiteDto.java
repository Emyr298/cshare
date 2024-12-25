package com.cshare.user.dto.users;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserLiteDto {
    @NotNull
    private String username;

    @NotNull
    private String name;
}
