ALTER TABLE "users" DROP CONSTRAINT "unique_username";
ALTER TABLE "users" DROP CONSTRAINT "unique_email";
ALTER TABLE "follows" DROP CONSTRAINT "unique_relation";

ALTER TABLE "users" ADD CONSTRAINT "unique_username" UNIQUE NULLS NOT DISTINCT ("username", "deleted_at");
ALTER TABLE "users" ADD CONSTRAINT "unique_email" UNIQUE NULLS NOT DISTINCT ("email", "deleted_at");
ALTER TABLE "follows" ADD CONSTRAINT "unique_relation" UNIQUE NULLS NOT DISTINCT ("followed_id", "follower_id", "deleted_at");