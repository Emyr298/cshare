import { IProfileResponse } from "@/lib/api";
import { OAuthProvider } from "@/types";

export interface IAuthMutationParams {
  providerAccessToken: string;
  provider: OAuthProvider;
}

export interface IUser {
  profile: IProfileResponse;
  isAuthenticated: boolean;
}
