package com.cshare.user.dto.follow;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FollowDto {
    @NotNull
    private String followedId;
}
