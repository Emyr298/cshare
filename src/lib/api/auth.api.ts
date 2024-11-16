import axios from "axios";
import { OAuthProvider } from "@/types/oauth";
import { ClientConfig } from "@/config";

export const fetchAuthCredentials = (accessToken: string, provider: OAuthProvider) => {
  axios.post(`${ClientConfig.apiUrl}/api/v1/auth/login`, {
    accessToken,
    provider,
  });
};
