import React from "react";
import { ProfileModuleProps } from "./interface";
import {
  ProfileAbout,
  ProfileHeader,
  ProfilePost,
} from "@/components/elements";

export const ProfileModule: React.FC<ProfileModuleProps> = ({ username }) => {
  return (
    <div>
      <div className="w-3/4">
        <ProfileHeader username={username} />
        <div className="flex mt-4 gap-4">
          <div className="w-64">
            <ProfileAbout username={username} />
          </div>
          <div className="flex-1">
            <ProfilePost username={username} />
          </div>
        </div>
      </div>
    </div>
  );
};
