import axios, { AxiosError, AxiosResponse } from "axios";
import { OAuthProvider } from "@/types/oauth";
import { ClientConfig } from "@/config";
import { IAuthCredentialsResponse, IProfileResponse } from "./auth.interface";
import { IBaseApiResponse } from "./base.interface";
import { z } from "zod";
import { registerSchema } from "../schemas/register";
import { NotRegisteredError } from "../errors/not-registered.error";

export const fetchAuthCredentials = async (
  providerAccessToken: string,
  provider: OAuthProvider,
) => {
  let response: AxiosResponse<IBaseApiResponse<IAuthCredentialsResponse>>;
  try {
    response = await axios.post(`${ClientConfig.apiUrl}/api/v1/auth/login`, {
      providerAccessToken,
      provider,
    });
  } catch (error) {
    if (error instanceof AxiosError && error.response?.data.message) {
      if (error.response.data.message == "Email is not registered") {
        throw new NotRegisteredError(error.response?.data.message);
      } else {
        throw new Error(error.response?.data.message);
      }
    }
    throw new Error("Could not connect to server");
  }
  return response.data.data!;
};

export const fetchUser = async (accessToken: string) => {
  const response: AxiosResponse<IBaseApiResponse<IProfileResponse>> =
    await axios.get(`${ClientConfig.apiUrl}/api/v1/users/current`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    });
  if (response.status != 200 || !response.data.data) {
    if (response.data?.message) {
      throw new Error(response.data.message);
    } else {
      throw new Error("Could not connect to server");
    }
  }
  return response.data.data;
};

export const registerUser = async (
  payload: z.infer<typeof registerSchema>,
  providerAccessToken: string,
  provider: OAuthProvider,
) => {
  const formData = new FormData();
  formData.append("avatarImage", payload.avatarImage);
  formData.append("coverImage", payload.coverImage);
  formData.append("username", payload.username);
  formData.append("name", payload.name);
  formData.append("provider", provider);
  formData.append("providerAccessToken", providerAccessToken);

  const response: AxiosResponse<IBaseApiResponse<IAuthCredentialsResponse>> =
    await axios.post(`${ClientConfig.apiUrl}/api/v1/auth/register`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  if (response.status != 200 || !response.data.data) {
    if (response.data?.message) {
      throw new Error(response.data.message);
    } else {
      throw new Error("Could not connect to server");
    }
  }
  return response.data.data;
};
