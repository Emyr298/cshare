import { z } from "zod";
import { ACCEPTED_IMAGE_TYPES, MAX_FILE_SIZE } from "./constants";

export const registerSchema = z.object({
  name: z.string().min(2, {
    message: "Name must be at least 2 characters.",
  }),
  username: z.string().min(8, {
    message: "Username must be at least 8 characters.",
  }),
  bio: z.string().optional(),
  avatarImage: z
    .any()
    .refine(
      (file) => (file ? file.size <= MAX_FILE_SIZE : true),
      "Avatar size must be at most 5 MB.",
    )
    .refine(
      (file) => (file ? ACCEPTED_IMAGE_TYPES.includes(file?.type) : true),
      `Avatar must have type ${ACCEPTED_IMAGE_TYPES.join(", ")}.`,
    ),
  coverImage: z
    .any()
    .refine(
      (file) => (file ? file.size <= MAX_FILE_SIZE : true),
      "Cover size must be at most 5 MB.",
    )
    .refine(
      (file) => (file ? ACCEPTED_IMAGE_TYPES.includes(file?.type) : true),
      `Cover must have type ${ACCEPTED_IMAGE_TYPES.join(", ")}.`,
    ),
});
