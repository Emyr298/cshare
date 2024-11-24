export interface AuthState {
  accessToken: string | null;
  refreshToken: string | null;
  setAccessToken: (accessToken: string | null) => void,
  setRefreshToken: (refreshToken: string | null) => void,
}
