import React from "react";
import { ProfileModuleProps } from "./interface";
import { ProfileHeader } from "@/components/elements";

export const ProfileModule: React.FC<ProfileModuleProps> = ({ username }) => {
  return (
    <div>
      <div className="w-3/4">
        <ProfileHeader username={username} />
      </div>
    </div>
  );
};
