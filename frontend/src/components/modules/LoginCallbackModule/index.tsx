import { fetchAuthCredentials } from "@/lib/api/auth.api";
import { LoginCallbackModuleProps } from "./interface";
import { useQuery } from "@tanstack/react-query";
import { writing } from "./constants";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

export const LoginCallbackModule: React.FC<LoginCallbackModuleProps> = ({
  accessToken,
  provider,
}) => {
  const router = useRouter();

  const { data, error, isLoading } = useQuery({
    queryKey: ["auth", accessToken, provider],
    queryFn: async () => {
      if (accessToken.length === 0) {
        throw new Error("invalid access token");
      }
      return await fetchAuthCredentials(accessToken, provider);
    },
  });

  useEffect(() => {
    if (!isLoading && data) {
      router.replace("/");
    }
  }, [data, isLoading]);

  const title = writing[error ? "error" : "loading"].title;
  const description = writing[error ? "error" : "loading"].description;

  return (
    <div className="h-screen -m-4 flex items-center justify-center">
      <div className="border-[1px] border-secondary rounded-md w-96 p-4 shadow-md bg-primary-foreground flex flex-col items-center gap-2">
        <div className="flex flex-col items-center">
          <h1 className="font-bold text-xl">{title}</h1>
          <span className="text-gray-500">{description}</span>
        </div>
      </div>
    </div>
  );
};
