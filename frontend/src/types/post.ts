import { UserPublic } from "./user";

export interface Post {
  title: string;
  description: string;
  user: UserPublic;
}

export interface PostSneakPeek {
  title: string;
  peek: string;
  user: UserPublic;
}
