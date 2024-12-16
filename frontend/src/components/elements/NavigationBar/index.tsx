"use client";

import { Button } from "@/components/ui/button";
import { Home, Users } from "lucide-react";
import { SearchBar } from "../SearchBar";
import { ProfileButton } from "../ProfileButton";
import Link from "next/link";
import { ModeToggle } from "../ModeToggle";
import { usePathname } from "next/navigation";
import { ClientConfig } from "@/config";
import { useAuth } from "@/components/queries";
import { guestCheck } from "@/lib/auth";
import { User } from "@/types";
import React from "react";

export default function NavigationBar() {
  const pathname = usePathname();

  const { data: user, isLoading: isUserLoading } = useAuth();

  const isHidden = ClientConfig.hiddenNavbarPaths.some((path) => {
    const escapedPath = path.replace("/", "\\/");
    const regex = new RegExp(`^${escapedPath}(/|$)`);
    return RegExp(regex).exec(pathname);
  });

  if (isHidden) return <></>;

  return (
    <nav className="w-full px-64 py-2 border-b-2 border-secondary bg-primary-foreground">
      <div className="flex justify-between items-center">
        <div className="flex flex-row items-center gap-4">
          <Link href="/" className="text-xl font-bold">
            CShare
          </Link>
          <SearchBar />
        </div>
        <div className="flex flex-row items-center gap-2">
          <ModeToggle />
          {isUserLoading ? <div>Loading..</div> : <Profile user={user!.data} />}
        </div>
      </div>
    </nav>
  );
}

const Profile: React.FC<{
  user: User;
}> = ({ user }) => {
  if (guestCheck(user)) {
    return (
      <>
        <Link href="/login">
          <Button className="w-fit p-4" variant="outline" size="icon">
            Login
          </Button>
        </Link>
        <Link href="/login">
          <Button className="w-fit p-4" variant="default" size="icon">
            Create Account
          </Button>
        </Link>
      </>
    );
  } else {
    return (
      <>
        <Button variant="ghost" size="icon">
          <Home />
        </Button>
        <Button variant="ghost" size="icon">
          <Users />
        </Button>
        {user.username}
        <ProfileButton />
      </>
    );
  }
};
