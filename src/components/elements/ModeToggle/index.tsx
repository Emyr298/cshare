"use client"
 
import * as React from "react"
import { Moon, Sun } from "lucide-react"
import { useTheme } from "next-themes"
 
import { Button } from "@/components/ui/button"

export function ModeToggle() {
  const { resolvedTheme, setTheme } = useTheme()
 
  return (
    <Button
      variant="ghost"
      size="icon"
      onClick={() => {
        if (resolvedTheme === 'dark') {
          setTheme('light');
        } else {
          setTheme('dark');
        }
      }}
    >
       { resolvedTheme === 'dark' && <Moon /> }
       { resolvedTheme === 'light' && <Sun /> }
    </Button>
  )
}