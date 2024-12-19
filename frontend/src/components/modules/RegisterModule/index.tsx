import React from "react";
import { RegisterModuleProps } from "./interface";
import { RegisterForm } from "@/components/elements/RegisterForm";

export const RegisterModule: React.FC<RegisterModuleProps> = () => {
  return (
    <div className="w-full flex flex-col items-center justify-center">
      <div className="w-128 rounded-md overflow-hidden border-2">
        <RegisterForm />
      </div>
    </div>
  );
};
