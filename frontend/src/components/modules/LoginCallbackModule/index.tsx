"use client";

import { LoginCallbackModuleProps } from "./interface";
import { writing } from "./constants";
import { useRouter } from "next/navigation";
import { useEffect } from "react";
import { useAuthMutation } from "@/components/queries";
import { NotRegisteredError } from "@/lib/errors/not-registered.error";

export const LoginCallbackModule: React.FC<LoginCallbackModuleProps> = ({
  accessToken,
  provider,
}) => {
  const router = useRouter();
  const { data, error, mutate } = useAuthMutation();

  useEffect(() => {
    if (accessToken && provider) {
      mutate({
        providerAccessToken: accessToken,
        provider: provider,
      });
    }
  }, [mutate, accessToken, provider]);

  useEffect(() => {
    if (data) {
      router.replace("/");
    } else if (error && error instanceof NotRegisteredError) {
      const params = new URLSearchParams(location.hash);
      params.append("provider", provider);
      router.replace(`/register${decodeURIComponent(params.toString())}`);
    }
  }, [data, error]);

  const title =
    writing[
      error && !(error instanceof NotRegisteredError) ? "error" : "loading"
    ].title;
  const description =
    writing[
      error && !(error instanceof NotRegisteredError) ? "error" : "loading"
    ].description;

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
