ALTER TABLE "users" ADD CONSTRAINT unique_username UNIQUE ("username", "deleted_at");
ALTER TABLE "users" ADD CONSTRAINT unique_email UNIQUE ("email", "deleted_at");

ALTER TABLE "users" ALTER COLUMN "created_at" SET DEFAULT NOW();
ALTER TABLE "users" ALTER COLUMN "updated_at" SET DEFAULT NOW();
ALTER TABLE "users" ALTER COLUMN "deleted_at" DROP NOT NULL;
ALTER TABLE "follows" ALTER COLUMN "created_at" SET DEFAULT NOW();
ALTER TABLE "follows" ALTER COLUMN "updated_at" SET DEFAULT NOW();
ALTER TABLE "follows" ALTER COLUMN "deleted_at" DROP NOT NULL;
