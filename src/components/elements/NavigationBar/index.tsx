"use client"

import { Button } from "@/components/ui/button";
import { Home, Users } from "lucide-react";
import { SearchBar } from "../SearchBar";
import { ProfileButton } from "../ProfileButton";
import Link from "next/link";
import { ModeToggle } from "../ModeToggle";
import { usePathname } from "next/navigation";
import { ClientConfig } from "@/config";

export default function NavigationBar() {
  const pathname = usePathname();

  const isHidden = ClientConfig.hiddenNavbarPaths.some((path) => {
    const escapedPath = path.replace('/', '\\/');
    const regex = new RegExp(`^${escapedPath}(\/|$)`);
    return pathname.match(regex);
  });

  if (isHidden) return <></>

  return (
    <nav className="w-full px-64 py-2 border-b-2 border-secondary bg-primary-foreground">
      <div className="flex justify-between items-center">
        <div className="flex flex-row items-center gap-4">
          <Link href="/" className="text-xl font-bold">CShare</Link>
          <SearchBar />
        </div>
        <div className="flex flex-row items-center gap-2">
          <ModeToggle />
          <Button variant="ghost" size="icon">
            <Home />
          </Button>
          <Button variant="ghost" size="icon">
            <Users />
          </Button>
          <ProfileButton />
        </div>
      </div>
    </nav>
  );
}
