"use client"

import { Button } from "@/components/ui/button";
import { googleSignIn } from "@/lib/auth";
import { FcGoogle } from "react-icons/fc"

export default function LoginModule() {
  return (
    <div className="h-screen -m-4 flex items-center justify-center">
      <div className="border-[1px] border-secondary rounded-md w-96 p-4 shadow-md bg-primary-foreground flex flex-col items-center gap-2">
        <div className="flex flex-col items-center">
          <h1 className="font-bold text-xl">Login to CShare</h1>
          <span className="text-gray-500">Share your contents to the world!</span>
        </div>
        <div>
          <Button
            onClick={googleSignIn}
            variant="outline"
          >
            <FcGoogle /> Continue with Google
          </Button>
        </div>
      </div>
    </div>
  );
}
