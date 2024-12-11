package com.cshare.user.models;

import java.time.LocalDateTime;
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
@Table("users")
public class User {
    @Id
    @Column("id")
    private UUID id;

    @Column("username")
    private String username;

    @Column("email")
    private String email;

    @Column("name")
    private String name;

    @Column("bio")
    private String bio;

    @Column("avatar_url")
    private String avatarUrl;

    @Column("cover_url")
    private String coverUrl;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("deleted_at")
    private LocalDateTime deletedAt;
}
