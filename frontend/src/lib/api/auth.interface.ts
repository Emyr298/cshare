export interface IAuthCredentialsResponse {
  accessToken: string;
  refreshToken: string;
}

export interface IProfileResponse {
  username: string;
  firstName: string;
  lastName: string;
  avatarUrl: string;
}
