import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { AuthMutationParams, AuthInfo, RegisterParams } from "./auth.interface";
import { fetchAuthCredentials, fetchUser, registerUser } from "@/lib/api";
import { useAuthStore } from "../stores";
import { GUEST_USER } from "@/lib/auth";

export const useAuth = () => {
  const accessToken = useAuthStore((state) => state.accessToken);
  return useQuery({
    queryKey: ["auth"],
    queryFn: async (): Promise<AuthInfo> => {
      if (!accessToken) {
        throw new Error("access token cannot be null");
      }

      let data = GUEST_USER;

      let isAuthenticated = false;
      try {
        data = await fetchUser(String(accessToken));
        isAuthenticated = true;
      } catch {
        isAuthenticated = false;
      }

      return { data, isAuthenticated };
    },
  });
};

export const useAuthMutation = () => {
  const queryClient = useQueryClient();

  const setAccessToken = useAuthStore((state) => state.setAccessToken);
  const setRefreshToken = useAuthStore((state) => state.setRefreshToken);

  return useMutation({
    mutationFn: async (data: AuthMutationParams) => {
      if (data.providerAccessToken.length === 0) {
        throw new Error("invalid provider access token");
      }

      const { accessToken, refreshToken } = await fetchAuthCredentials(
        data.providerAccessToken,
        data.provider,
      );

      setAccessToken(accessToken);
      setRefreshToken(refreshToken);

      queryClient.invalidateQueries({ queryKey: ["auth"] });

      return { accessToken, refreshToken };
    },
  });
};

export const useRegister = () => {
  const queryClient = useQueryClient();

  const setAccessToken = useAuthStore((state) => state.setAccessToken);
  const setRefreshToken = useAuthStore((state) => state.setRefreshToken);

  return useMutation({
    mutationFn: async (data: RegisterParams) => {
      if (data.providerAccessToken.length === 0) {
        throw new Error("invalid provider access token");
      }

      const { accessToken, refreshToken } = await registerUser(
        data.payload,
        data.providerAccessToken,
        data.provider,
      );

      setAccessToken(accessToken);
      setRefreshToken(refreshToken);

      queryClient.invalidateQueries({ queryKey: ["auth"] });

      return { accessToken, refreshToken };
    },
  });
};
