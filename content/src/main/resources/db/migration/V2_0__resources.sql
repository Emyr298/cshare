CREATE TABLE IF NOT EXISTS "content_resources" (
    "id" uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    "content_id" uuid NOT NULL,
    "url" text NOT NULL,
    "created_at" TIMESTAMP NOT NULL DEFAULT NOW(),

    FOREIGN KEY ("content_id") REFERENCES "content"("id") ON DELETE CASCADE ON UPDATE CASCADE
);
