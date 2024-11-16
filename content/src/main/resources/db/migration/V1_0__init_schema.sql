CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TYPE "content_status" AS ENUM ('DRAFT', 'PUBLISHED');

CREATE TABLE IF NOT EXISTS "content" (
    "id" uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    "title" varchar(255) NOT NULL,
    "description" text NOT NULL,
    "user_id" uuid NOT NULL,
    "status" content_status NOT NULL
);