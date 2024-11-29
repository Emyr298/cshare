import * as React from "react";
import { ProfilePostProps } from "./interface";
import { POST_PLACEHOLDER } from "./constants";

export const ProfilePost: React.FC<ProfilePostProps> = ({ username }) => {
  return (
    <div className="w-full">
      <div>
        {POST_PLACEHOLDER.map((post) => (
          <div key={post.id}>
            <span>{post.title}</span>
          </div>
        ))}
      </div>
    </div>
  );
};
