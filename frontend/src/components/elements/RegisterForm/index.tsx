"use client";

import React, { useState } from "react";
import { zodResolver } from "@hookform/resolvers/zod";
import { RegisterFormProps } from "./interface";
import { ACCEPTED_IMAGE_TYPES_PROP } from "./constants";
import Image from "next/image";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { registerSchema } from "@/lib/schemas/register";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";
import { FaAngleRight, FaPen } from "react-icons/fa";

export const RegisterForm: React.FC<RegisterFormProps> = ({
  onSubmit,
  errorMessage,
}) => {
  const [avatarBlobUrl, setAvatarBlobUrl] = useState<string | null>(null);
  const [coverBlobUrl, setCoverBlobUrl] = useState<string | null>(null);

  const form = useForm<z.infer<typeof registerSchema>>({
    resolver: zodResolver(registerSchema),
    defaultValues: {
      name: "",
      username: "",
    },
  });

  const nameInitial = form
    .watch("name")
    .split(" ")
    .filter((word) => !!word)
    .map((word) => (word ? word[0].toUpperCase() : ""))
    .slice(0, 2)
    .join("");

  return (
    <div className="w-full">
      <Form {...form}>
        <form
          onSubmit={form.handleSubmit(onSubmit)}
          className="flex flex-col items-center"
        >
          <FormField
            control={form.control}
            name="coverImage"
            render={({ field }) => (
              <FormItem className="w-full h-48 border-b-2 bg-black">
                <FormLabel className="font-bold">
                  <div className="w-full h-full relative">
                    {coverBlobUrl ? (
                      <Image
                        src={coverBlobUrl}
                        alt={`avatar`}
                        width={300}
                        height={300}
                        className="w-full h-full object-cover"
                      />
                    ) : (
                      <div className="w-full h-full bg-gray-400"></div>
                    )}
                    <div className="cursor-pointer transition-opacity duration-200 ease-in-out hover:opacity-100 opacity-0 bg-black bg-opacity-30 w-full h-full relative -top-[100%] flex justify-end p-4">
                      <FaPen className="w-6 h-6 text-white" />
                    </div>
                  </div>
                </FormLabel>
                <FormControl>
                  <Input
                    placeholder="File"
                    type="file"
                    className="hidden"
                    accept={ACCEPTED_IMAGE_TYPES_PROP}
                    onChange={(e) => {
                      const file = e.target.files?.[0];
                      if (file) {
                        const blobUrl = URL.createObjectURL(file);
                        setCoverBlobUrl(blobUrl);
                      } else {
                        setCoverBlobUrl(null);
                      }
                      return field.onChange(e.target.files?.[0]);
                    }}
                  />
                </FormControl>
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="avatarImage"
            render={({ field }) => (
              <FormItem className="z-10 -mt-[150px] flex flex-col items-center">
                <FormLabel className="font-bold">
                  <div className="border-2 z-50  w-[200px] h-[200px] rounded-full overflow-hidden">
                    {avatarBlobUrl ? (
                      <Image
                        src={avatarBlobUrl}
                        alt={`avatar`}
                        width={300}
                        height={300}
                        className="w-full h-full object-cover"
                      />
                    ) : (
                      <div className="w-full h-full bg-gray-200 flex items-center justify-center">
                        <span className="text-7xl text-black">
                          {nameInitial}
                        </span>
                      </div>
                    )}
                    <div className="cursor-pointer transition-opacity duration-200 ease-in-out hover:opacity-100 opacity-0 bg-black bg-opacity-30 w-full h-full relative -top-[100%] flex items-center justify-center">
                      <FaPen className="w-8 h-8 text-white" />
                    </div>
                  </div>
                </FormLabel>
                <FormControl>
                  <Input
                    placeholder="File"
                    type="file"
                    className="hidden"
                    accept={ACCEPTED_IMAGE_TYPES_PROP}
                    onChange={(e) => {
                      const file = e.target.files?.[0];
                      if (file) {
                        const blobUrl = URL.createObjectURL(file);
                        setAvatarBlobUrl(blobUrl);
                      } else {
                        setAvatarBlobUrl(null);
                      }
                      return field.onChange(e.target.files?.[0]);
                    }}
                  />
                </FormControl>
              </FormItem>
            )}
          />
          <div className="flex flex-col pt-2 px-4">
            <span className="text-[0.8rem] font-medium text-destructive">
              {form.formState.errors.avatarImage?.message}
            </span>
            <span className="text-[0.8rem] font-medium text-destructive">
              {form.formState.errors.coverImage?.message}
            </span>
          </div>
          <div className="w-full px-8 py-4 flex flex-col items-end gap-2">
            <div className="w-full flex gap-4">
              <FormField
                control={form.control}
                name="username"
                render={({ field }) => (
                  <FormItem className="w-full">
                    <FormLabel className="font-bold">Username</FormLabel>
                    <FormControl>
                      <Input placeholder="Username" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="name"
                render={({ field }) => (
                  <FormItem className="w-full">
                    <FormLabel className="font-bold">Name</FormLabel>
                    <FormControl>
                      <Input placeholder="Name" {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
            </div>
            <FormField
              control={form.control}
              name="bio"
              render={({ field }) => (
                <FormItem className="w-full">
                  <FormLabel className="font-bold">Bio</FormLabel>
                  <FormControl>
                    <Textarea
                      placeholder="Bio"
                      className="resize-none"
                      {...field}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <Button type="submit" className="mt-2 pr-3">
              Complete <FaAngleRight />
            </Button>
            <span className="block text-[0.8rem] font-medium text-destructive">
              {errorMessage}
            </span>
          </div>
        </form>
      </Form>
    </div>
  );
};
