"use client";

import { RegisterModule } from "@/components/modules/RegisterModule";
import { OAuthProvider } from "@/types";
import { useEffect, useState } from "react";

export default function Register() {
  const [isMounted, setIsMounted] = useState(false);
  const [accessToken, setAccessToken] = useState("");
  const [provider, setProvider] = useState<OAuthProvider>(OAuthProvider.GOOGLE);

  useEffect(() => {
    if (isMounted) {
      const params = new URLSearchParams(location.hash.substring(1));

      const accessTokenParams = params.get("access_token") ?? "";
      setAccessToken(accessTokenParams);

      const providerParams = params.get("provider") ?? "";
      if (OAuthProvider[providerParams as OAuthProvider]) {
        setProvider(OAuthProvider[providerParams as OAuthProvider]);
      }
    } else {
      setIsMounted(true);
    }
  });

  return (
    <RegisterModule provider={provider} providerAccessToken={accessToken} />
  );
}
