import { OAuthProvider } from "@/types/oauth";
import { LoginCallbackModuleProps } from "./interface";
import { fetchAuthCredentials } from "@/lib/api/auth.api";

export const LoginCallbackModule: React.FC<LoginCallbackModuleProps> = ({
    provider,
}) => {
  return (
    <div className="h-screen -m-4 flex items-center justify-center">
      <div className="border-[1px] border-secondary rounded-md w-96 p-4 shadow-md bg-primary-foreground flex flex-col items-center gap-2">
        <div className="flex flex-col items-center">
          <h1 className="font-bold text-xl">Redirecting..</h1>
          <span className="text-gray-500">It can take seconds</span>
        </div>
      </div>
    </div>
  );
}
