package com.cshare.user.dto.follow;

import java.util.List;

import com.cshare.user.dto.Response;
import com.cshare.user.models.User;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class FollowedResponse extends Response {
    private List<User> users;
}
