import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { IAuthMutationParams, IUser } from "./auth.interface";
import { fetchAuthCredentials, fetchUser, IProfileResponse } from "@/lib/api";
import { useAuthStore } from "../stores";

export const useAuth = () => {
  const accessToken = useAuthStore((state) => state.accessToken);

  return useQuery({
    queryKey: ["auth"],
    queryFn: async (): Promise<IUser> => {
      let profile = {
        username: "guest",
        firstName: "Guest",
        lastName: "",
        avatarUrl: "",
      };

      let isAuthenticated = false;
      try {
        profile = await fetchUser(String(accessToken));
        isAuthenticated = true;
      } catch {
        isAuthenticated = false;
      }

      return { profile, isAuthenticated };
    },
  });
};

export const useAuthMutation = () => {
  const queryClient = useQueryClient();

  const setAccessToken = useAuthStore((state) => state.setAccessToken);
  const setRefreshToken = useAuthStore((state) => state.setRefreshToken);

  return useMutation({
    mutationFn: async (data: IAuthMutationParams) => {
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
