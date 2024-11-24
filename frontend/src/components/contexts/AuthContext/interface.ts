import { User } from "@/types";

export interface AuthContextInterface {
  user?: User;
  token?: string;
  isLoading: boolean;
}
