import { useContext } from "react";
import { AuthContext } from "./context";
import { useQuery } from "@tanstack/react-query";
import { fetchUser } from "@/lib/api";

export const AuthContextProvider: React.FC<{
  children: React.ReactNode;
}> = ({ children }) => {
  // const { data, error, isLoading } = useQuery({
  //   queryKey: ["auth", accessToken, provider],
  //   queryFn: async () => {
  //     if (accessToken.length === 0) {
  //       throw new Error("invalid access token");
  //     }
  //     return await fetchAuthCredentials(accessToken, provider);
  //   },
  // });

  // useQuery({
  //   queryKey: ["user"],
  //   queryFn: async () => await fetchUser(accessToken),
  // });

  return (
    <AuthContext.Provider value={{ isLoading: true }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuthContext = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error("useAuthContext must be used within AuthContextProvider");
  }
  return context;
};
