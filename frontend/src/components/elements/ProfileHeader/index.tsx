import * as React from "react";
import { ProfileHeaderProps } from "./interface";
import Image from "next/image";
import { AVATAR_PLACEHOLDER, COVER_PLACEHOLDER } from "./constants";

export const ProfileHeader: React.FC<ProfileHeaderProps> = ({ username }) => {
  return (
    <div className="w-full border-2 border-secondary rounded-md">
      <div>
        <div className=" w-full h-48">
          <Image
            src={COVER_PLACEHOLDER}
            alt={`cover picture of ${username}`}
            width={1400}
            height={300}
            className="w-full h-full object-cover -z-10 relative"
          />
        </div>
        <div className="border-4 border-primary-foreground z-50 ml-7 -mt-[100px] w-[200px] h-[200px] rounded-full overflow-hidden">
          <Image
            src={AVATAR_PLACEHOLDER}
            alt={`avatar of ${username}`}
            width={300}
            height={300}
            className="w-full"
          />
        </div>
      </div>
      <div className="-mt-[100px] pl-64 py-4 w-full min-h-[110px]">
        <div className="w-full">
          <h1 className="text-2xl font-bold">Fligger</h1>
          <span className="text-primary">{`@${username}`}</span>
          <div className="flex pt-2 gap-3">
            <span>3 Followers</span>
            <span>4 Following</span>
          </div>
        </div>
      </div>
    </div>
  );
};
