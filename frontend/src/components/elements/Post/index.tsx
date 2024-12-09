import * as React from "react";
import { PostProps } from "./interface";
import { AVATAR_PLACEHOLDER } from "./constants";
import Image from "next/image";
import { IoHeartOutline, IoShareSocial } from "react-icons/io5";
import { Button } from "@/components/ui/button";
import { FaRegComment } from "react-icons/fa";

export const Post: React.FC<PostProps> = ({ post }) => {
  return (
    <div className="w-full border-2 border-secondary rounded-md overflow-hidden h-fit">
      <a
        href={`/@${post.user.username}`}
        className="w-fit px-4 pt-4 pb-2 flex items-center gap-2"
      >
        <div className="w-10 h-10 rounded-full overflow-hidden">
          <Image
            src={AVATAR_PLACEHOLDER}
            alt={`avatar of ${post.user.username}`}
            width={300}
            height={300}
            className="object-cover relative"
          />
        </div>
        <div>
          <span className="block">Fligger</span>
          <span className="block text-sm">@{post.user.username}</span>
        </div>
      </a>
      <a href="https://google.com" className="block px-4 pb-4">
        <h3 className="text-xl font-bold">{post.title}</h3>
        <p className="text-[0.95rem] mt-1">{post.peek}</p>
      </a>
      <div className="w-full flex">
        <Button variant="ghost" className="w-full rounded-none">
          <IoHeartOutline /> 5 Likes
        </Button>
        <Button variant="ghost" className="w-full rounded-none">
          <FaRegComment /> 2 Comments
        </Button>
        <Button variant="ghost" className="w-full rounded-none">
          <IoShareSocial /> 4 Shares
        </Button>
      </div>
    </div>
  );
};
