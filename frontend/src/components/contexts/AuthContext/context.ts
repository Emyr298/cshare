import { createContext } from "react";
import { AuthContextInterface } from "./interface";

export const AuthContext = createContext<AuthContextInterface | undefined>(
  undefined,
);
