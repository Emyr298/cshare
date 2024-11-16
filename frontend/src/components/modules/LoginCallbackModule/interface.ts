import { OAuthProvider } from "@/types/oauth";

export interface LoginCallbackModuleProps {
  accessToken: string;
  provider: OAuthProvider;
}
