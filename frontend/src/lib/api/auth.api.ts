import axios, { AxiosResponse } from "axios";
import { OAuthProvider } from "@/types/oauth";
import { ClientConfig } from "@/config";
import { IAuthCredentialsResponse } from "./auth.interface";
import { IBaseApiResponse } from "./base.interface";

export const fetchAuthCredentials = async (
  accessToken: string,
  provider: OAuthProvider,
) => {
  console.log("AWAITING");
  const response: AxiosResponse<IBaseApiResponse<IAuthCredentialsResponse>> = await axios.post(`${ClientConfig.apiUrl}/users/api/v1/auth/login`, {
    accessToken,
    provider,
  });
  if (response.status != 200) {
    if (response.data && response.data.message) {
      throw new Error(response.data.message);
    } else {
      throw new Error("Could not connect to server")
    }
  }
  return response.data.data;
};
