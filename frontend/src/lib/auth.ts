import { ClientConfig } from "@/config";
import { User } from "@/types";

export const googleSignIn = async () => {
  const oauthEndpoint = new URL(ClientConfig.oauthCredentials.google.endpoint);
  oauthEndpoint.search = new URLSearchParams(
    ClientConfig.oauthCredentials.google.params,
  ).toString();

  window.location.href = oauthEndpoint.toString();
};

export const GUEST_USER: User = {
  username: "guest",
  firstName: "Guest",
  lastName: "",
  avatarUrl: "",
};

export const guestCheck = (user: User) => {
  return user.username === GUEST_USER.username;
};
