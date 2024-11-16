import { Search } from "lucide-react";
import * as React from "react"

export const SearchBar = () => {
  return (
    <div className="flex items-center gap-1 min-w-64 h-8 w-full rounded-md border border-input bg-white px-2 py-1 text-base shadow-sm disabled:opacity-50 md:text-sm">
      <Search size={20} />
      <input
        type="text"
        placeholder="Search.."
        className="w-full outline-none bg-transparent"
      />
    </div>
  );
};
