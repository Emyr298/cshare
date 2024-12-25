import { registerSchema } from "@/lib/schemas/register";
import { z } from "zod";

export interface RegisterFormProps {
  onSubmit: (value: z.infer<typeof registerSchema>) => void;
  errorMessage?: string;
}
