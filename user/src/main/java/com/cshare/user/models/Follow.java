package com.cshare.user.models;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Table("follows")
public class Follow {
    @Id
    @Column("id")
    private UUID id;

    @Column("followed_id")
    private UUID followedId;

    @Column("follower_id")
    private UUID followerId;
}
