import { create } from "zustand";
import { AuthState } from "./auth.interface";
import { persist } from "zustand/middleware";

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      refreshToken: null,
      accessToken: null,
      setAccessToken: (accessToken: string | null) =>
        set(() => ({ accessToken: accessToken })),
      setRefreshToken: (refreshToken: string | null) =>
        set(() => ({ refreshToken: refreshToken })),
    }),
    {
      name: "auth-storage",
    },
  ),
);
