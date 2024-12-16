import { OAuthProvider, User } from "@/types";

export interface AuthMutationParams {
  providerAccessToken: string;
  provider: OAuthProvider;
}

export interface AuthInfo {
  data: User;
  isAuthenticated: boolean;
}
