import { LoginCallbackModule } from "@/components";
import { OAuthProvider } from "@/types/oauth";

export default function GoogleLoginCallback() {
  return <LoginCallbackModule provider={OAuthProvider.GOOGLE} />;
}
