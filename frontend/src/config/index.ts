export const ClientConfig = {
  hiddenNavbarPaths: ["/login"],
  oauthCredentials: {
    google: {
      endpoint: String(process.env.NEXT_PUBLIC_AUTH_GOOGLE_ENDPOINT),
      params: {
        client_id: String(process.env.NEXT_PUBLIC_AUTH_GOOGLE_CLIENT_ID),
        redirect_uri: String(process.env.NEXT_PUBLIC_AUTH_REDIRECT_URI),
        response_type: "token",
        scope: String(process.env.NEXT_PUBLIC_AUTH_SCOPE),
        include_granted_scopes: "true",
        state: "pass-through value",
      },
    },
  },
  apiUrl: String(process.env.NEXT_PUBLIC_API_URL),
};
