import * as React from "react";
import { ProfilePostProps } from "./interface";
import { POST_PLACEHOLDER } from "./constants";
import { Post } from "../Post";

export const ProfilePost: React.FC<ProfilePostProps> = ({ username }) => {
  return (
    <div className="w-full">
      <div className="flex flex-col gap-4">
        {POST_PLACEHOLDER.map((post) => (
          <div key={post.id}>
            <Post
              post={{
                title: post.title,
                peek: post.description,
                user: post.user,
              }}
            />
          </div>
        ))}
      </div>
    </div>
  );
};
