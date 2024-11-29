import * as React from "react";
import { ProfileAboutProps } from "./interface";

export const ProfileAbout: React.FC<ProfileAboutProps> = ({ username }) => {
  return (
    <div className="w-full border-2 border-secondary rounded-md overflow-hidden h-fit">
      <div className="w-full py-1 px-2 bg-secondary">
        <h2 className="font-bold">About Fligger</h2>
      </div>
      <div className="px-4 py-2">
        <p className="text-[0.95rem]">
          Lorem Ipsum is simply dummy text of the printing and typesetting
          industry. Lorem Ipsum has been the industry's standard dummy text ever
          since the 1500s, when an unknown printer took a galley of type and
          scrambled it to make a type specimen book.
        </p>
      </div>
    </div>
  );
};
