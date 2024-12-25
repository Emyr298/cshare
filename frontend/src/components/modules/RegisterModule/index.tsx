"use client";

import React, { useCallback, useEffect } from "react";
import { RegisterModuleProps } from "./interface";
import { RegisterForm } from "@/components/elements/RegisterForm";
import { useRegister } from "@/components/queries";
import { z } from "zod";
import { registerSchema } from "@/lib/schemas/register";
import { useRouter } from "next/navigation";

export const RegisterModule: React.FC<RegisterModuleProps> = ({
  provider,
  providerAccessToken,
}) => {
  const router = useRouter();
  const { data, error, mutate } = useRegister();

  const onSubmit = useCallback(
    (payload: z.infer<typeof registerSchema>) => {
      mutate({
        payload,
        provider,
        providerAccessToken,
      });
    },
    [mutate, provider, providerAccessToken],
  );

  useEffect(() => {
    if (data) {
      router.replace("/");
    }
  }, [data]);

  return (
    <div className="w-full flex flex-col items-center justify-center">
      <div className="w-128 rounded-md overflow-hidden border-2">
        <RegisterForm onSubmit={onSubmit} errorMessage={error?.message} />
      </div>
    </div>
  );
};
