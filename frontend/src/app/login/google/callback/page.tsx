"use client";

import { LoginCallbackModule } from "@/components";
import { OAuthProvider } from "@/types/oauth";
import { useEffect, useState } from "react";

export default function GoogleLoginCallback() {
  const [isMounted, setIsMounted] = useState(false);
  const [accessToken, setAccessToken] = useState("");

  useEffect(() => {
    if (isMounted) {
      const params = new URLSearchParams(location.hash.substring(1));
      setAccessToken(params.get("access_token") ?? "");
    } else {
      setIsMounted(true);
    }
  });

  return (
    <LoginCallbackModule
      accessToken={accessToken}
      provider={OAuthProvider.GOOGLE}
    />
  );
}
