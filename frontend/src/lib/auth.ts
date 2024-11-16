import { ClientConfig } from "@/config";

export const googleSignIn = async () => {
  const oauthEndpoint = new URL(ClientConfig.oauthCredentials.google.endpoint);
  oauthEndpoint.search = new URLSearchParams(ClientConfig.oauthCredentials.google.params).toString();
  
  window.location.href = oauthEndpoint.toString();
}
