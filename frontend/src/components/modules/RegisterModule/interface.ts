import { OAuthProvider } from "@/types";

export interface RegisterModuleProps {
  providerAccessToken: string;
  provider: OAuthProvider;
}
