import { registerSchema } from "@/lib/schemas/register";
import { OAuthProvider, User } from "@/types";
import { z } from "zod";

export interface AuthMutationParams {
  providerAccessToken: string;
  provider: OAuthProvider;
}

export interface AuthInfo {
  data: User;
  isAuthenticated: boolean;
}

export interface RegisterParams {
  payload: z.infer<typeof registerSchema>;
  provider: OAuthProvider;
  providerAccessToken: string;
}
