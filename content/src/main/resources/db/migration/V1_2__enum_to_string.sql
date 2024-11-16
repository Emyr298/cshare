ALTER TABLE "content" ALTER COLUMN "status" TYPE VARCHAR(32) USING "status"::VARCHAR;
ALTER TABLE "content" ADD CONSTRAINT "content_status_values" CHECK ("status" IN ('DRAFT', 'PUBLISHED'));

DROP TYPE IF EXISTS "content_status";
