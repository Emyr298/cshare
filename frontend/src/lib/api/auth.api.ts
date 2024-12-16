import axios, { AxiosResponse } from "axios";
import { OAuthProvider } from "@/types/oauth";
import { ClientConfig } from "@/config";
import { IAuthCredentialsResponse, IProfileResponse } from "./auth.interface";
import { IBaseApiResponse } from "./base.interface";

export const fetchAuthCredentials = async (
  providerAccessToken: string,
  provider: OAuthProvider,
) => {
  const response: AxiosResponse<IBaseApiResponse<IAuthCredentialsResponse>> =
    await axios.post(`${ClientConfig.apiUrl}/api/v1/auth/login`, {
      providerAccessToken,
      provider,
    });
  if (response.status != 200 || !response.data.data) {
    if (response.data && response.data.message) {
      throw new Error(response.data.message);
    } else {
      throw new Error("Could not connect to server");
    }
  }
  return response.data.data;
};

export const fetchUser = async (accessToken: string) => {
  const response: AxiosResponse<IBaseApiResponse<IProfileResponse>> =
    await axios.get(`${ClientConfig.apiUrl}/api/v1/users/current`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    });
  if (response.status != 200 || !response.data.data) {
    if (response.data && response.data.message) {
      throw new Error(response.data.message);
    } else {
      throw new Error("Could not connect to server");
    }
  }
  return response.data.data;
};
