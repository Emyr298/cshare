CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS "users" (
    "id" uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    "username" varchar(255) NOT NULL,
    "email" varchar(255) NOT NULL,
    "name" varchar(255) NOT NULL,
    "bio" varchar(255),
    "avatar_url" TEXT NOT NULL,
    "cover_url" TEXT NOT NULL,
    "created_at" TIMESTAMP NOT NULL,
    "updated_at" TIMESTAMP NOT NULL,
    "deleted_at" TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS "follows" (
    "id" uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    "followed_id" uuid NOT NULL,
    "follower_id" uuid NOT NULL,
    "created_at" TIMESTAMP NOT NULL,
    "updated_at" TIMESTAMP NOT NULL,
    "deleted_at" TIMESTAMP NOT NULL,
    FOREIGN KEY ("followed_id") REFERENCES "users"("id"),
    FOREIGN KEY ("follower_id") REFERENCES "users"("id"),
    CONSTRAINT unique_relation UNIQUE ("followed_id", "follower_id", "deleted_at")
);
